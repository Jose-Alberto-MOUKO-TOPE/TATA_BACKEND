package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.Administrador;
import com.tata.ecommerce.entity.Usuario;
import com.tata.ecommerce.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {
    
    @Autowired
    private AdministradorService administradorService;
    
    @PostMapping
    public ResponseEntity<Administrador> crearAdministrador(@RequestBody Map<String, Object> payload) {
        Usuario usuario = new Usuario();
        usuario.setNombre((String) payload.get("nombre"));
        usuario.setEmail((String) payload.get("email"));
        usuario.setPassword((String) payload.get("password"));
        usuario.setRol(com.tata.ecommerce.entity.enums.Rol.ADMIN);
        
        Administrador admin = new Administrador();
        admin.setCargo((String) payload.get("cargo"));
        
        Object nivelAccesoObj = payload.get("nivelAcceso");
        if (nivelAccesoObj instanceof Integer) {
            admin.setNivelAcceso((Integer) nivelAccesoObj);
        }
        
        Administrador nuevoAdmin = administradorService.crearAdministrador(admin, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAdmin);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obtenerAdministrador(@PathVariable Long id) {
        Administrador admin = administradorService.obtenerPorId(id);
        return ResponseEntity.ok(admin);
    }
    
    @GetMapping
    public ResponseEntity<List<Administrador>> obtenerTodos() {
        List<Administrador> admins = administradorService.obtenerTodos();
        return ResponseEntity.ok(admins);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Administrador> obtenerPorUsuario(@PathVariable Long usuarioId) {
        Administrador admin = administradorService.obtenerPorUsuarioId(usuarioId);
        return ResponseEntity.ok(admin);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> actualizarAdministrador(@PathVariable Long id, @RequestBody Administrador admin) {
        Administrador adminActualizado = administradorService.actualizarAdministrador(id, admin);
        return ResponseEntity.ok(adminActualizado);
    }
}
