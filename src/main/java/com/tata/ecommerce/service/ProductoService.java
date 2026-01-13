package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Categoria;
import com.tata.ecommerce.entity.Producto;
import com.tata.ecommerce.entity.enums.EstadoCategoria;
import com.tata.ecommerce.entity.enums.EstadoProducto;
import com.tata.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Transactional
    public Producto solicitarNuevoProducto(Producto producto, Long vendedorId) {
        Categoria categoria = categoriaService.obtenerPorId(producto.getCategoria().getId());
        
        if (categoria.getEstado() != EstadoCategoria.APROBADA) {
            throw new RuntimeException("Solo se pueden crear productos en categorías APROBADAS");
        }
        
        if (productoRepository.findByNombre(producto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un producto con ese nombre");
        }
        
        producto.setEstado(EstadoProducto.PENDIENTE);
        producto.setFechaCreacion(LocalDateTime.now());
        producto.setVendedorSolicitanteId(vendedorId);
        
        Producto nuevoProducto = productoRepository.save(producto);
        
        auditoriaService.registrar(
                vendedorId,
            "SOLICITUD_PRODUCTO",
            "Vendedor " + vendedorId + " solicitó nuevo producto: " + producto.getNombre()

        );
        
        return nuevoProducto;
    }
    
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    
    public Producto obtenerPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre)
            .orElse(null);
    }
    
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    
    public List<Producto> obtenerPorEstado(EstadoProducto estado) {
        return productoRepository.findByEstado(estado);
    }
    
    public List<Producto> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }
    
    public List<Producto> obtenerPorVendedor(Long vendedorId) {
        return productoRepository.findByVendedorSolicitanteId(vendedorId);
    }
    
    @Transactional
    public Producto aprobarProducto(Long productoId, Long adminId) {
        Producto producto = obtenerPorId(productoId);
        
        if (producto.getEstado() != EstadoProducto.PENDIENTE) {
            throw new RuntimeException("Solo se pueden aprobar productos en estado PENDIENTE");
        }
        
        producto.setEstado(EstadoProducto.APROBADO);
        producto.setFechaAprobacion(LocalDateTime.now());
        producto.setAdminAprobadorId(adminId);
        
        Producto productoGuardado = productoRepository.save(producto);
        
        auditoriaService.registrar(
                adminId,
            "APROBACION_PRODUCTO",
            "Admin " + adminId + " aprobó producto: " + producto.getNombre()

        );
        
        return productoGuardado;
    }
    
    @Transactional
    public Producto rechazarProducto(Long productoId, Long adminId, String motivo) {
        Producto producto = obtenerPorId(productoId);
        
        if (producto.getEstado() != EstadoProducto.PENDIENTE) {
            throw new RuntimeException("Solo se pueden rechazar productos en estado PENDIENTE");
        }
        
        producto.setEstado(EstadoProducto.RECHAZADO);
        producto.setAdminAprobadorId(adminId);
        
        Producto productoGuardado = productoRepository.save(producto);
        
        auditoriaService.registrar(
                adminId,
            "RECHAZO_PRODUCTO",
            "Admin " + adminId + " rechazó producto: " + producto.getNombre() + ". Motivo: " + motivo
        );
        
        return productoGuardado;
    }
    
    @Transactional
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto producto = obtenerPorId(id);
        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setMarca(productoActualizado.getMarca());
        return productoRepository.save(producto);
    }
    
    public boolean estaAprobado(Long productoId) {
        Producto producto = obtenerPorId(productoId);
        return producto.getEstado() == EstadoProducto.APROBADO;
    }

    public Producto crearProducto(Producto producto) {
        return producto;
    }
}
