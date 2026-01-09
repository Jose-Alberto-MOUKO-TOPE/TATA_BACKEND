package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.Usuario;
import com.tata.ecommerce.entity.Vendedor;
import com.tata.ecommerce.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {
    
    @Autowired
    private VendedorService vendedorService;
    
    @PostMapping
    public ResponseEntity<Vendedor> crearVendedor(@RequestBody Map<String, Object> payload) {
        Usuario usuario = new Usuario();
        usuario.setNombre((String) payload.get("nombre"));
        usuario.setEmail((String) payload.get("email"));
        usuario.setPassword((String) payload.get("password"));
        usuario.setRol(com.tata.ecommerce.entity.enums.Rol.VENDEDOR);
        
        Vendedor vendedor = new Vendedor();
        vendedor.setNombreTienda((String) payload.get("nombreTienda"));
        vendedor.setTelefono((String) payload.get("telefono"));
        vendedor.setDireccion((String) payload.get("direccion"));
        
        Vendedor nuevoVendedor = vendedorService.crearVendedor(vendedor, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVendedor);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> obtenerVendedor(@PathVariable Long id) {
        Vendedor vendedor = vendedorService.obtenerPorId(id);
        return ResponseEntity.ok(vendedor);
    }
    
    @GetMapping
    public ResponseEntity<List<Vendedor>> obtenerTodos() {
        List<Vendedor> vendedores = vendedorService.obtenerTodos();
        return ResponseEntity.ok(vendedores);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Vendedor> obtenerPorUsuario(@PathVariable Long usuarioId) {
        Vendedor vendedor = vendedorService.obtenerPorUsuarioId(usuarioId);
        return ResponseEntity.ok(vendedor);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> actualizarVendedor(@PathVariable Long id, @RequestBody Vendedor vendedor) {
        Vendedor vendedorActualizado = vendedorService.actualizarVendedor(id, vendedor);
        return ResponseEntity.ok(vendedorActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVendedor(@PathVariable Long id) {
        vendedorService.eliminarVendedor(id);
        return ResponseEntity.noContent().build();
    }
}
