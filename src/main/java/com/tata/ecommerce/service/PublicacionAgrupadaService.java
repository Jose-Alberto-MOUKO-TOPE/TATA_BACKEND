package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Articulo;
import com.tata.ecommerce.entity.PublicacionAgrupada;
import com.tata.ecommerce.entity.enums.EstadoArticulo;
import com.tata.ecommerce.repository.ArticuloRepository;
import com.tata.ecommerce.repository.PublicacionAgrupadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicacionAgrupadaService {
    
    @Autowired
    private PublicacionAgrupadaRepository publicacionAgrupadaRepository;
    
    @Autowired
    private ArticuloRepository articuloRepository;
    
    @Transactional
    public PublicacionAgrupada crearOActualizar(Long productoId, Long categoriaId) {
        PublicacionAgrupada publicacion = publicacionAgrupadaRepository.findByProductoId(productoId)
            .orElse(new PublicacionAgrupada());
        
        if (publicacion.getId() == null) {
            publicacion.getProducto().setId(productoId);
            publicacion.getCategoria().setId(categoriaId);
            publicacion.setFechaCreacion(LocalDateTime.now());
        }
        
        List<Articulo> articulos = articuloRepository.findByProductoIdAndEstadoIn(
            productoId, 
            List.of(EstadoArticulo.DISPONIBLE, EstadoArticulo.RESERVADO)
        );
        
        publicacion.setTotalArticulos(articulos.size());
        
        if (!articulos.isEmpty()) {
            BigDecimal precioMin = articulos.stream()
                .map(Articulo::getPrecio)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
            
            BigDecimal precioMax = articulos.stream()
                .map(Articulo::getPrecio)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
            
            publicacion.setPrecioMinimo(precioMin);
            publicacion.setPrecioMaximo(precioMax);
        }
        
        publicacion.setFechaActualizacion(LocalDateTime.now());
        
        return publicacionAgrupadaRepository.save(publicacion);
    }
    
    public PublicacionAgrupada obtenerPorId(Long id) {
        return publicacionAgrupadaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publicación agrupada no encontrada"));
    }
    
    public PublicacionAgrupada obtenerPorProducto(Long productoId) {
        return publicacionAgrupadaRepository.findByProductoId(productoId)
            .orElse(null);
    }
    
    public List<PublicacionAgrupada> obtenerPorCategoria(Long categoriaId) {
        return publicacionAgrupadaRepository.findByCategoriaId(categoriaId);
    }
    
    public List<PublicacionAgrupada> obtenerTodas() {
        return publicacionAgrupadaRepository.findAll();
    }
}
