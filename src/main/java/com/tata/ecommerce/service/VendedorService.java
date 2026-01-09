package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Usuario;
import com.tata.ecommerce.entity.Vendedor;
import com.tata.ecommerce.entity.enums.Rol;
import com.tata.ecommerce.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendedorService {
    
    @Autowired
    private VendedorRepository vendedorRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Transactional
    public Vendedor crearVendedor(Vendedor vendedor, Usuario usuario) {
        if (usuario.getRol() != Rol.VENDEDOR) {
            throw new RuntimeException("El usuario debe tener rol VENDEDOR");
        }
        Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);
        vendedor.setUsuario(usuarioGuardado);
        return vendedorRepository.save(vendedor);
    }
    
    public Vendedor obtenerPorId(Long id) {
        return vendedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
    }
    
    public Vendedor obtenerPorUsuarioId(Long usuarioId) {
        return vendedorRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Vendedor no encontrado para el usuario"));
    }
    
    public List<Vendedor> obtenerTodos() {
        return vendedorRepository.findAll();
    }
    
    @Transactional
    public Vendedor actualizarVendedor(Long id, Vendedor vendedorActualizado) {
        Vendedor vendedor = obtenerPorId(id);
        vendedor.setNombreTienda(vendedorActualizado.getNombreTienda());
        vendedor.setTelefono(vendedorActualizado.getTelefono());
        vendedor.setDireccion(vendedorActualizado.getDireccion());
        return vendedorRepository.save(vendedor);
    }
    
    @Transactional
    public void eliminarVendedor(Long id) {
        Vendedor vendedor = obtenerPorId(id);
        usuarioService.desactivarUsuario(vendedor.getUsuario().getId());
    }
}
