package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Usuario;
import com.alberto.laesquina.repositorio.UsuarioRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServ {

    private final UsuarioRep usuarioRep;

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRep.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        return usuarioRep.save(usuario);
    }

    public Usuario obtenerPorEmail(String email) {
        return usuarioRep.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario obtenerPorId(Long idUsuario) {
        return usuarioRep.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}

