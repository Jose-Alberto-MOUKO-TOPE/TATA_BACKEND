package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.Articulo;
import com.tata.ecommerce.entity.enums.EstadoArticulo;
import com.tata.ecommerce.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {
    
    @Autowired
    private ArticuloService articuloService;
    
    @PostMapping
    public ResponseEntity<Articulo> publicarArticulo(@RequestBody Articulo articulo) {
        Articulo nuevoArticulo = articuloService.publicarArticulo(articulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoArticulo);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerArticulo(@PathVariable Long id) {
        Articulo articulo = articuloService.obtenerPorId(id);
        return ResponseEntity.ok(articulo);
    }
    
    @GetMapping
    public ResponseEntity<List<Articulo>> obtenerTodos() {
        List<Articulo> articulos = articuloService.obtenerTodos();
        return ResponseEntity.ok(articulos);
    }
    
    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<Articulo>> obtenerPorVendedor(@PathVariable Long vendedorId) {
        List<Articulo> articulos = articuloService.obtenerPorVendedor(vendedorId);
        return ResponseEntity.ok(articulos);
    }
    
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Articulo>> obtenerPorProducto(@PathVariable Long productoId) {
        List<Articulo> articulos = articuloService.obtenerPorProducto(productoId);
        return ResponseEntity.ok(articulos);
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Articulo>> obtenerPorEstado(@PathVariable EstadoArticulo estado) {
        List<Articulo> articulos = articuloService.obtenerPorEstado(estado);
        return ResponseEntity.ok(articulos);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizarArticulo(@PathVariable Long id, @RequestBody Articulo articulo) {
        Articulo articuloActualizado = articuloService.actualizarArticulo(id, articulo);
        return ResponseEntity.ok(articuloActualizado);
    }
    
    @PatchMapping("/{id}/reservar")
    public ResponseEntity<Articulo> reservarArticulo(@PathVariable Long id) {
        Articulo articuloReservado = articuloService.reservarArticulo(id);
        return ResponseEntity.ok(articuloReservado);
    }
    
    @PatchMapping("/{id}/vender")
    public ResponseEntity<Articulo> marcarComoVendido(@PathVariable Long id) {
        Articulo articuloVendido = articuloService.marcarComoVendido(id);
        return ResponseEntity.ok(articuloVendido);
    }
    
    @PatchMapping("/{id}/disponible")
    public ResponseEntity<Articulo> hacerDisponible(@PathVariable Long id) {
        Articulo articuloDisponible = articuloService.hacerDisponible(id);
        return ResponseEntity.ok(articuloDisponible);
    }
}
