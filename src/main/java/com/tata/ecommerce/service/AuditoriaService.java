package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Auditoria;
import com.tata.ecommerce.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaService {
    
    @Autowired
    private AuditoriaRepository auditoriaRepository;
    
    @Transactional
    public Auditoria registrarAccion(Long actorId, String accion, String descripcion) {
        Auditoria auditoria = new Auditoria();
        auditoria.setActorId(actorId);
        auditoria.setAccion(accion);
        auditoria.setDescripcion(descripcion);
        auditoria.setFecha(LocalDateTime.now());
        return auditoriaRepository.save(auditoria);
    }
    
    public List<Auditoria> obtenerTodas() {
        return auditoriaRepository.findAll();
    }
    
    public List<Auditoria> obtenerPorActor(Long actorId) {
        return auditoriaRepository.findByActorId(actorId);
    }
    
    public List<Auditoria> obtenerPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return auditoriaRepository.findByFechaBetween(inicio, fin);
    }
    
    public Auditoria obtenerPorId(Long id) {
        return auditoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Auditoría no encontrada"));
    }
}
