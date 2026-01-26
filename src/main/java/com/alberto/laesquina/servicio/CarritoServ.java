package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Carrito;
import com.alberto.laesquina.entidad.Cliente;
import com.alberto.laesquina.repositorio.CarritoRep;
import com.alberto.laesquina.repositorio.ClienteRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarritoServ {

    private final CarritoRep carritoRep;
    private final ClienteRep clienteRep;

    public Carrito crearCarritoParaCliente(Long idCliente) {
        Cliente cliente = clienteRep.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Verificar si ya tiene carrito
        Optional<Carrito> carritoExistente = carritoRep.findByIdCliente(idCliente);
        if (carritoExistente.isPresent()) {
            return carritoExistente.get();
        }

        Carrito carrito = new Carrito();
        carrito.setCliente(cliente);
        carrito.setActivo(true);

        return carritoRep.save(carrito);
    }

    public Carrito obtenerCarritoPorCliente(Long idCliente) {
        return carritoRep.findByIdCliente(idCliente)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el cliente"));
    }

    public Carrito obtenerCarritoPorId(Long idCarrito) {
        return carritoRep.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    public void desactivarCarrito(Long idCarrito) {
        Carrito carrito = obtenerCarritoPorId(idCarrito);
        carrito.setActivo(false);
        carritoRep.save(carrito);
    }

    public void activarCarrito(Long idCarrito) {
        Carrito carrito = obtenerCarritoPorId(idCarrito);
        carrito.setActivo(true);
        carritoRep.save(carrito);
    }

    public void eliminarCarrito(Long idCarrito) {
        carritoRep.deleteById(idCarrito);
    }
}