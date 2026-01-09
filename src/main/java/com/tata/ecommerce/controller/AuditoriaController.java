package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.Auditoria;
import com.tata.ecommerce.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auditorias")
public class AuditoriaController {
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @GetMapping
    public ResponseEntity<List<Auditoria>> obtenerTodas() {
        List<Auditoria> auditorias = auditoriaService.obtenerTodas();
        return ResponseEntity.ok(auditorias);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Auditoria> obtenerAuditoria(@PathVariable Long id) {
        Auditoria auditoria = auditoriaService.obtenerPorId(id);
        return ResponseEntity.ok(auditoria);
    }
    
    @GetMapping("/actor/{actorId}")
    public ResponseEntity<List<Auditoria>> obtenerPorActor(@PathVariable Long actorId) {
        List<Auditoria> auditorias = auditoriaService.obtenerPorActor(actorId);
        return ResponseEntity.ok(auditorias);
    }
    
    @GetMapping("/rango")
    public ResponseEntity<List<Auditoria>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<Auditoria> auditorias = auditoriaService.obtenerPorRangoFechas(inicio, fin);
        return ResponseEntity.ok(auditorias);
    }
}
