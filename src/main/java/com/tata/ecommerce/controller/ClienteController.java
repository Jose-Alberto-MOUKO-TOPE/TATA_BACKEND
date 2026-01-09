package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.Cliente;
import com.tata.ecommerce.entity.Usuario;
import com.tata.ecommerce.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Map<String, Object> payload) {
        Usuario usuario = new Usuario();
        usuario.setNombre((String) payload.get("nombre"));
        usuario.setEmail((String) payload.get("email"));
        usuario.setPassword((String) payload.get("password"));
        usuario.setRol(com.tata.ecommerce.entity.enums.Rol.CLIENTE);
        
        Cliente cliente = new Cliente();
        cliente.setDireccion((String) payload.get("direccion"));
        cliente.setTelefono((String) payload.get("telefono"));
        
        Cliente nuevoCliente = clienteService.crearCliente(cliente, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(cliente);
    }
    
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        List<Cliente> clientes = clienteService.obtenerTodos();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Cliente> obtenerPorUsuario(@PathVariable Long usuarioId) {
        Cliente cliente = clienteService.obtenerPorUsuarioId(usuarioId);
        return ResponseEntity.ok(cliente);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
        return ResponseEntity.ok(clienteActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
