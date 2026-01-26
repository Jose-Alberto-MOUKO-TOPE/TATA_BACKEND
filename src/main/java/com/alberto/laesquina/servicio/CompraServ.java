package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Compra;
import com.alberto.laesquina.entidad.Cliente;
import com.alberto.laesquina.repositorio.CompraRep;
import com.alberto.laesquina.repositorio.ClienteRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompraServ {

    private final CompraRep compraRep;
    private final ClienteRep clienteRep;

    public Compra crearCompra(Long idCliente, Double total) {
        Cliente cliente = clienteRep.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTotal(total);
        compra.setFecha(LocalDateTime.now());

        return compraRep.save(compra);
    }

    public Compra obtenerPorId(Long id) {
        return compraRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
    }

    public List<Compra> obtenerPorCliente(Long idCliente) {
        return compraRep.findByCliente_IdCliente(idCliente);
    }

    public List<Compra> obtenerTodas() {
        return compraRep.findAll();
    }

    public List<Compra> obtenerPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return compraRep.findAll().stream()
                .filter(compra -> !compra.getFecha().isBefore(inicio) && !compra.getFecha().isAfter(fin))
                .toList();
    }

    public Double obtenerTotalComprasCliente(Long idCliente) {
        return compraRep.findByCliente_IdCliente(idCliente).stream()
                .mapToDouble(Compra::getTotal)
                .sum();
    }

    public void eliminarCompra(Long id) {
        compraRep.deleteById(id);
    }
}