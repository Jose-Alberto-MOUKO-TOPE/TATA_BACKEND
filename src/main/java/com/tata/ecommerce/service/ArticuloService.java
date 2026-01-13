package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Articulo;
import com.tata.ecommerce.entity.Categoria;
import com.tata.ecommerce.entity.Producto;
import com.tata.ecommerce.entity.Vendedor;
import com.tata.ecommerce.entity.enums.EstadoArticulo;
import com.tata.ecommerce.entity.enums.EstadoCategoria;
import com.tata.ecommerce.entity.enums.EstadoProducto;
import com.tata.ecommerce.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticuloService {
    
    @Autowired
    private ArticuloRepository articuloRepository;
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private VendedorService vendedorService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private SlotService slotService;
    
    @Autowired
    private PublicacionAgrupadaService publicacionAgrupadaService;
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @Transactional
    public Articulo publicarArticulo(Articulo articulo) {
        Producto producto = productoService.obtenerPorId(articulo.getProducto().getId());
        
        if (producto.getEstado() != EstadoProducto.APROBADO) {
            throw new RuntimeException("No se puede publicar un artículo de un producto que no está APROBADO");
        }
        
        Categoria categoria = categoriaService.obtenerPorId(producto.getCategoria().getId());
        
        if (categoria.getEstado() != EstadoCategoria.APROBADA) {
            throw new RuntimeException("No se puede publicar en una categoría que no está APROBADA");
        }
        
        Vendedor vendedor = vendedorService.obtenerPorId(articulo.getVendedor().getId());
        
        if (!vendedor.getUsuario().getActivo()) {
            throw new RuntimeException("El vendedor no está activo");
        }
        
        if (!slotService.tieneSlotDisponible(vendedor.getId(), categoria.getId())) {
            throw new RuntimeException("No tienes slots disponibles en esta categoría. " +
                "Slots disponibles: " + slotService.obtenerSlotsDisponibles(vendedor.getId(), categoria.getId()));
        }
        
        articulo.setEstado(EstadoArticulo.DISPONIBLE);
        articulo.setFechaPublicacion(LocalDateTime.now());
        
        Articulo articuloGuardado = articuloRepository.save(articulo);
        
        publicacionAgrupadaService.crearOActualizar(producto.getId(), categoria.getId());
        
        auditoriaService.registrar(
                vendedor.getId(),
            "PUBLICACION_ARTICULO",
            "Vendedor " + vendedor.getId() + " publicó artículo en producto: " + producto.getNombre()
        );
        
        return articuloGuardado;
    }
    
    public Articulo obtenerPorId(Long id) {
        return articuloRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
    }
    
    public List<Articulo> obtenerTodos() {
        return articuloRepository.findAll();
    }
    
    public List<Articulo> obtenerPorVendedor(Long vendedorId) {
        return articuloRepository.findByVendedorId(vendedorId);
    }
    
    public List<Articulo> obtenerPorProducto(Long productoId) {
        return articuloRepository.findByProductoId(productoId);
    }
    
    public List<Articulo> obtenerPorEstado(EstadoArticulo estado) {
        return articuloRepository.findByEstado(estado);
    }
    
    public List<Articulo> obtenerPorPublicacionAgrupada(Long publicacionAgrupadaId) {
        return articuloRepository.findByPublicacionAgrupadaId(publicacionAgrupadaId);
    }
    
    @Transactional
    public Articulo actualizarArticulo(Long id, Articulo articuloActualizado) {
        Articulo articulo = obtenerPorId(id);
        articulo.setPrecio(articuloActualizado.getPrecio());
        articulo.setStock(articuloActualizado.getStock());
        articulo.setCondicion(articuloActualizado.getCondicion());
        articulo.setImagenUrl(articuloActualizado.getImagenUrl());
        
        Articulo guardado = articuloRepository.save(articulo);
        
        publicacionAgrupadaService.crearOActualizar(
            articulo.getProducto().getId(), 
            articulo.getProducto().getCategoria().getId()
        );
        
        return guardado;
    }
    
    @Transactional
    public Articulo cambiarEstado(Long id, EstadoArticulo nuevoEstado) {
        Articulo articulo = obtenerPorId(id);
        
        if (articulo.getEstado() == EstadoArticulo.VENDIDO) {
            throw new RuntimeException("No se puede cambiar el estado de un artículo ya VENDIDO");
        }
        
        articulo.setEstado(nuevoEstado);
        Articulo guardado = articuloRepository.save(articulo);
        
        publicacionAgrupadaService.crearOActualizar(
            articulo.getProducto().getId(), 
            articulo.getProducto().getCategoria().getId()
        );
        
        return guardado;
    }
    
    @Transactional
    public Articulo reservarArticulo(Long id) {
        return cambiarEstado(id, EstadoArticulo.RESERVADO);
    }
    
    @Transactional
    public Articulo marcarComoVendido(Long id) {
        Articulo articulo = obtenerPorId(id);
        
        if (articulo.getStock() <= 0) {
            throw new RuntimeException("No hay stock disponible");
        }
        
        articulo.setStock(articulo.getStock() - 1);
        
        if (articulo.getStock() == 0) {
            articulo.setEstado(EstadoArticulo.VENDIDO);
        }
        
        Articulo guardado = articuloRepository.save(articulo);
        
        publicacionAgrupadaService.crearOActualizar(
            articulo.getProducto().getId(), 
            articulo.getProducto().getCategoria().getId()
        );
        
        auditoriaService.registrar(
                articulo.getVendedor().getId(),
            "VENTA_ARTICULO",
            "Artículo " + id + " marcado como VENDIDO. Slot liberado en categoría " + 
            articulo.getProducto().getCategoria().getId()
        );
        
        return guardado;
    }
    
    @Transactional
    public Articulo hacerDisponible(Long id) {
        return cambiarEstado(id, EstadoArticulo.DISPONIBLE);
    }
}
