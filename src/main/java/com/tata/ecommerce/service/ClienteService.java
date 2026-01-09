package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Cliente;
import com.tata.ecommerce.entity.Usuario;
import com.tata.ecommerce.entity.enums.Rol;
import com.tata.ecommerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Transactional
    public Cliente crearCliente(Cliente cliente, Usuario usuario) {
        if (usuario.getRol() != Rol.CLIENTE) {
            throw new RuntimeException("El usuario debe tener rol CLIENTE");
        }
        Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);
        cliente.setUsuario(usuarioGuardado);
        return clienteRepository.save(cliente);
    }
    
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
    
    public Cliente obtenerPorUsuarioId(Long usuarioId) {
        return clienteRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuario"));
    }
    
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }
    
    @Transactional
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        Cliente cliente = obtenerPorId(id);
        cliente.setDireccion(clienteActualizado.getDireccion());
        cliente.setTelefono(clienteActualizado.getTelefono());
        return clienteRepository.save(cliente);
    }
    
    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = obtenerPorId(id);
        usuarioService.desactivarUsuario(cliente.getUsuario().getId());
    }
}
