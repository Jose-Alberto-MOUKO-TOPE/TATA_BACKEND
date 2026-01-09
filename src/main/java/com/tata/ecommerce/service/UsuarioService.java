package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Usuario;
import com.tata.ecommerce.entity.enums.Rol;
import com.tata.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }
    
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }
    
    public List<Usuario> obtenerPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }
    
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuario = obtenerPorId(id);
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setEmail(usuarioActualizado.getEmail());
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            usuario.setPassword(usuarioActualizado.getPassword());
        }
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void desactivarUsuario(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void activarUsuario(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }
    
    public boolean validarCredenciales(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElse(null);
        if (usuario == null || !usuario.getActivo()) {
            return false;
        }
        return usuario.getPassword().equals(password);
    }
}
