package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.Categoria;
import com.tata.ecommerce.entity.enums.EstadoCategoria;
import com.tata.ecommerce.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;
    
    @PostMapping("/solicitar")
    public ResponseEntity<Categoria> solicitarNuevaCategoria(
        @RequestBody Categoria categoria,
        @RequestParam Long vendedorId
    ) {
        return ResponseEntity.ok(categoriaService.solicitarNuevaCategoria(categoria, vendedorId));
    }
    
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Categoria> aprobarCategoria(
        @PathVariable Long id,
        @RequestParam Long adminId
    ) {
        return ResponseEntity.ok(categoriaService.aprobarCategoria(id, adminId));
    }
    
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Categoria> rechazarCategoria(
        @PathVariable Long id,
        @RequestParam Long adminId
    ) {
        return ResponseEntity.ok(categoriaService.rechazarCategoria(id, adminId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }
    
    @GetMapping("/aprobadas")
    public ResponseEntity<List<Categoria>> obtenerAprobadas() {
        return ResponseEntity.ok(categoriaService.obtenerAprobadas());
    }
    
    @GetMapping("/pendientes")
    public ResponseEntity<List<Categoria>> obtenerPendientes() {
        return ResponseEntity.ok(categoriaService.obtenerPendientes());
    }
    
    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<Categoria>> obtenerPorVendedor(@PathVariable Long vendedorId) {
        return ResponseEntity.ok(categoriaService.obtenerPorVendedor(vendedorId));
    }
}
