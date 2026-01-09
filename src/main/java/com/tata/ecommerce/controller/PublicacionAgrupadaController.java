package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.Articulo;
import com.tata.ecommerce.entity.PublicacionAgrupada;
import com.tata.ecommerce.service.ArticuloService;
import com.tata.ecommerce.service.PublicacionAgrupadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/publicaciones-agrupadas")
public class PublicacionAgrupadaController {
    
    @Autowired
    private PublicacionAgrupadaService publicacionAgrupadaService;
    
    @Autowired
    private ArticuloService articuloService;
    
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionAgrupada> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(publicacionAgrupadaService.obtenerPorId(id));
    }
    
    @GetMapping("/{id}/detalle")
    public ResponseEntity<Map<String, Object>> obtenerDetalleCompleto(@PathVariable Long id) {
        PublicacionAgrupada publicacion = publicacionAgrupadaService.obtenerPorId(id);
        List<Articulo> articulos = articuloService.obtenerPorPublicacionAgrupada(id);
        
        Map<String, Object> detalle = new HashMap<>();
        detalle.put("publicacion", publicacion);
        detalle.put("articulos", articulos);
        
        return ResponseEntity.ok(detalle);
    }
    
    @GetMapping
    public ResponseEntity<List<PublicacionAgrupada>> obtenerTodas() {
        return ResponseEntity.ok(publicacionAgrupadaService.obtenerTodas());
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<PublicacionAgrupada>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(publicacionAgrupadaService.obtenerPorCategoria(categoriaId));
    }
    
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<PublicacionAgrupada> obtenerPorProducto(@PathVariable Long productoId) {
        PublicacionAgrupada publicacion = publicacionAgrupadaService.obtenerPorProducto(productoId);
        if (publicacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicacion);
    }
}
