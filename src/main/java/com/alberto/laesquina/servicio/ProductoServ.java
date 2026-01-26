package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.EstadoProducto;
import com.alberto.laesquina.entidad.Producto;
import com.alberto.laesquina.repositorio.ProductoRep;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoServ {

    @Autowired
    private final ProductoRep productoRep;
    private final AuditoriaServ auditoriaServ;

    // Propuesto por vendedor
    public Producto proponerProducto(Producto producto) {
        producto.setEstado(EstadoProducto.PENDIENTE);
        return productoRep.save(producto);
    }

    // ADMIN
    public void aprobarProducto(Long idProducto, Long idAdministrador) {
        Producto producto = productoRep.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado(EstadoProducto.APROBADO);
        productoRep.save(producto);

        auditoriaServ.registrar(
                idAdministrador,
                "APROBACIÓN_PRODUCTO",
                "Producto aprobado: " + producto.getNombre()
        );
    }

    public void rechazarProducto(Long idProducto, Long idAdministrador, String motivo) {
        Producto producto = productoRep.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado(EstadoProducto.RECHAZADO);
        productoRep.save(producto);

        auditoriaServ.registrar(
                idAdministrador,
                "RECHAZO_PRODUCTO",
                motivo
        );
    }
}

