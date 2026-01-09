package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Administrador;
import com.tata.ecommerce.entity.Usuario;
import com.tata.ecommerce.entity.enums.Rol;
import com.tata.ecommerce.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdministradorService {
    
    @Autowired
    private AdministradorRepository administradorRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Transactional
    public Administrador crearAdministrador(Administrador administrador, Usuario usuario) {
        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("El usuario debe tener rol ADMIN");
        }
        Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);
        administrador.setUsuario(usuarioGuardado);
        return administradorRepository.save(administrador);
    }
    
    public Administrador obtenerPorId(Long id) {
        return administradorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    }
    
    public Administrador obtenerPorUsuarioId(Long usuarioId) {
        return administradorRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado para el usuario"));
    }
    
    public List<Administrador> obtenerTodos() {
        return administradorRepository.findAll();
    }
    
    @Transactional
    public Administrador actualizarAdministrador(Long id, Administrador adminActualizado) {
        Administrador admin = obtenerPorId(id);
        admin.setCargo(adminActualizado.getCargo());
        admin.setNivelAcceso(adminActualizado.getNivelAcceso());
        return administradorRepository.save(admin);
    }
}
