package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Cliente;
import com.alberto.laesquina.repositorio.ClienteRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServ {

    private final ClienteRep clienteRep;

    public Cliente crearCliente(Cliente cliente) {
        return clienteRep.save(cliente);
    }

    public Cliente obtenerPorUsuario(Long idUsuario) {
        return clienteRep.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
}

