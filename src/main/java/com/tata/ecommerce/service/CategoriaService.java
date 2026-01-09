package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Categoria;
import com.tata.ecommerce.entity.enums.EstadoCategoria;
import com.tata.ecommerce.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @Transactional
    public Categoria solicitarNuevaCategoria(Categoria categoria, Long vendedorId) {
        if (categoriaRepository.findByNombre(categoria.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        
        categoria.setEstado(EstadoCategoria.PENDIENTE);
        categoria.setVendedorSolicitanteId(vendedorId);
        categoria.setFechaCreacion(LocalDateTime.now());
        
        Categoria nuevaCategoria = categoriaRepository.save(categoria);
        
        auditoriaService.registrar(
            "SOLICITUD_CATEGORIA",
            "Vendedor " + vendedorId + " solicitó nueva categoría: " + categoria.getNombre(),
            vendedorId,
            null
        );
        
        return nuevaCategoria;
    }
    
    @Transactional
    public Categoria aprobarCategoria(Long categoriaId, Long adminId) {
        Categoria categoria = obtenerPorId(categoriaId);
        
        if (categoria.getEstado() != EstadoCategoria.PENDIENTE) {
            throw new RuntimeException("Solo se pueden aprobar categorías PENDIENTES");
        }
        
        categoria.setEstado(EstadoCategoria.APROBADA);
        categoria.setFechaAprobacion(LocalDateTime.now());
        categoria.setAdminAprobadorId(adminId);
        
        Categoria categoriaAprobada = categoriaRepository.save(categoria);
        
        auditoriaService.registrar(
            "APROBACION_CATEGORIA",
            "Admin " + adminId + " aprobó categoría: " + categoria.getNombre(),
            null,
            adminId
        );
        
        return categoriaAprobada;
    }
    
    @Transactional
    public Categoria rechazarCategoria(Long categoriaId, Long adminId) {
        Categoria categoria = obtenerPorId(categoriaId);
        
        if (categoria.getEstado() != EstadoCategoria.PENDIENTE) {
            throw new RuntimeException("Solo se pueden rechazar categorías PENDIENTES");
        }
        
        categoria.setEstado(EstadoCategoria.RECHAZADA);
        categoria.setAdminAprobadorId(adminId);
        
        Categoria categoriaRechazada = categoriaRepository.save(categoria);
        
        auditoriaService.registrar(
            "RECHAZO_CATEGORIA",
            "Admin " + adminId + " rechazó categoría: " + categoria.getNombre(),
            null,
            adminId
        );
        
        return categoriaRechazada;
    }
    
    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
    
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }
    
    public List<Categoria> obtenerAprobadas() {
        return categoriaRepository.findByEstado(EstadoCategoria.APROBADA);
    }
    
    public List<Categoria> obtenerPendientes() {
        return categoriaRepository.findByEstado(EstadoCategoria.PENDIENTE);
    }
    
    public List<Categoria> obtenerPorVendedor(Long vendedorId) {
        return categoriaRepository.findByVendedorSolicitanteId(vendedorId);
    }
}
