package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.Producto;
import com.tata.ecommerce.entity.enums.EstadoProducto;
import com.tata.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }
    
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Producto>> obtenerPorEstado(@PathVariable EstadoProducto estado) {
        List<Producto> productos = productoService.obtenerPorEstado(estado);
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable String categoria) {
        Long categoriaId = 0L;
        List<Producto> productos = productoService.obtenerPorCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }
    
    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<Producto> aprobarProducto(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Long adminId = Long.valueOf(payload.get("adminId").toString());
        Producto productoAprobado = productoService.aprobarProducto(id, adminId);
        return ResponseEntity.ok(productoAprobado);
    }
    
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<Producto> rechazarProducto(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Long adminId = Long.valueOf(payload.get("adminId").toString());
        String motivo = (String) payload.get("motivo");
        Producto productoRechazado = productoService.rechazarProducto(id, adminId, motivo);
        return ResponseEntity.ok(productoRechazado);
    }
}
