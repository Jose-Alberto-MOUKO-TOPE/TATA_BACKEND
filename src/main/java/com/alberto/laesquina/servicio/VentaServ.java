package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Venta;
import com.alberto.laesquina.entidad.Vendedor;
import com.alberto.laesquina.entidad.Articulo;
import com.alberto.laesquina.repositorio.VentaRep;
import com.alberto.laesquina.repositorio.VendedorRep;
import com.alberto.laesquina.repositorio.ArticuloRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VentaServ {

    private final VentaRep ventaRep;
    private final VendedorRep vendedorRep;
    private final ArticuloRep articuloRep;

    public Venta registrarVenta(Long idVendedor, Long idArticulo, Double precio) {
        Vendedor vendedor = vendedorRep.findById(idVendedor)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        Articulo articulo = articuloRep.findById(idArticulo)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

        // Validar que el artículo pertenece al vendedor
        if (!articulo.getVendedor().getIdVendedor().equals(idVendedor)) {
            throw new RuntimeException("El artículo no pertenece a este vendedor");
        }

        // Validar que el artículo está disponible o reservado para este comprador
        // (necesitarías lógica adicional para verificar reservas)

        // Validar precio
        if (precio <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0");
        }

        Venta venta = new Venta();
        venta.setVendedor(vendedor);
        venta.setArticulo(articulo);
        venta.setPrecio(precio);
        venta.setFecha(LocalDateTime.now());

        // Aquí deberías cambiar el estado del artículo a VENDIDO
        // articulo.setEstado(EstadoArticulo.VENDIDO);
        // articuloRep.save(articulo);

        return ventaRep.save(venta);
    }

    public Venta obtenerPorId(Long id) {
        return ventaRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public List<Venta> obtenerPorVendedor(Long idVendedor) {
        return ventaRep.findByVendedor_IdVendedor(idVendedor);
    }

    public List<Venta> obtenerTodas() {
        return ventaRep.findAll();
    }

    public List<Venta> obtenerPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRep.findAll().stream()
                .filter(venta -> !venta.getFecha().isBefore(inicio) && !venta.getFecha().isAfter(fin))
                .toList();
    }

    public Double obtenerTotalVentasVendedor(Long idVendedor) {
        return ventaRep.findByVendedor_IdVendedor(idVendedor).stream()
                .mapToDouble(Venta::getPrecio)
                .sum();
    }

    public Long obtenerCantidadVentasVendedor(Long idVendedor) {
        return (long) ventaRep.findByVendedor_IdVendedor(idVendedor).size();
    }

    public void eliminarVenta(Long id) {
        ventaRep.deleteById(id);
    }
}