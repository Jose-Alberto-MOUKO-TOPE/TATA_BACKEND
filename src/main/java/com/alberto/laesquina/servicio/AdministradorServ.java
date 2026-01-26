package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Articulo;
import com.alberto.laesquina.entidad.EstadoArticulo;
import com.alberto.laesquina.entidad.Producto;
import com.alberto.laesquina.entidad.EstadoProducto;
import com.alberto.laesquina.entidad.Vendedor;
import com.alberto.laesquina.repositorio.ArticuloRep;
import com.alberto.laesquina.repositorio.ProductoRep;
import com.alberto.laesquina.repositorio.VendedorRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministradorServ {

    private final ArticuloRep articuloRep;
    private final ProductoRep productoRep;
    private final VendedorRep vendedorRep;
    private final AuditoriaServ auditoriaServ;

    // MÉTODO PARA APROBAR ARTÍCULOS
    public void aprobarArticulo(Long idArticulo, Long idAdministrador) {
        Optional<Articulo> articuloOpt = articuloRep.findById(idArticulo);
        if (articuloOpt.isEmpty()) {
            throw new RuntimeException("Artículo no encontrado");
        }

        Articulo articulo = articuloOpt.get();
        articulo.setEstado(EstadoArticulo.DISPONIBLE);
        articuloRep.save(articulo);

        auditoriaServ.registrar(
                idAdministrador,
                "APROBACIÓN_ARTICULO",
                "Artículo aprobado: " + idArticulo
        );
    }

    // MÉTODO PARA RECHAZAR ARTÍCULOS
    public void rechazarArticulo(Long idArticulo, Long adminId, String motivo) {
        Optional<Articulo> articuloOpt = articuloRep.findById(idArticulo);
        if (articuloOpt.isEmpty()) {
            throw new RuntimeException("Artículo no encontrado");
        }

        Articulo articulo = articuloOpt.get();

        // Opción 1: Cambiar estado a RECHAZADO
        articulo.setEstado(EstadoArticulo.RECHAZADO);
        articuloRep.save(articulo);

        // Opción 2: Eliminar el artículo
        // articuloRep.delete(articulo);

        auditoriaServ.registrar(
                adminId,
                "RECHAZO_ARTICULO",
                motivo
        );
    }

    // MÉTODO PARA APROBAR PRODUCTOS
    public void aprobarProducto(Long idProducto, Long idAdministrador) {
        Optional<Producto> productoOpt = productoRep.findById(idProducto);
        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto no encontrado");
        }

        Producto producto = productoOpt.get();
        producto.setEstado(EstadoProducto.APROBADO);
        productoRep.save(producto);

        auditoriaServ.registrar(
                idAdministrador,
                "APROBACIÓN_PRODUCTO",
                "Producto aprobado: " + idProducto
        );
    }

    // MÉTODO PARA RECHAZAR PRODUCTOS
    public void rechazarProducto(Long idProducto, Long adminId, String motivo) {
        Optional<Producto> productoOpt = productoRep.findById(idProducto);
        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto no encontrado");
        }

        Producto producto = productoOpt.get();
        producto.setEstado(EstadoProducto.RECHAZADO);
        productoRep.save(producto);

        auditoriaServ.registrar(
                adminId,
                "RECHAZO_PRODUCTO",
                motivo
        );
    }

    // MÉTODO SIMPLIFICADO: APROBAR ARTÍCULO BASADO EN PRODUCTO
    public void aprobarArticuloConProducto(Long idArticulo, Long idProducto, Long idAdministrador) {
        // Verificar que el artículo existe
        Optional<Articulo> articuloOpt = articuloRep.findById(idArticulo);
        if (articuloOpt.isEmpty()) {
            throw new RuntimeException("Artículo no encontrado");
        }

        // Verificar que el producto existe y está aprobado
        Optional<Producto> productoOpt = productoRep.findById(idProducto);
        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto no encontrado");
        }

        Producto producto = productoOpt.get();
        if (producto.getEstado() != EstadoProducto.APROBADO) {
            throw new RuntimeException("El producto no está aprobado");
        }

        // Aprobar el artículo
        Articulo articulo = articuloOpt.get();
        articulo.setEstado(EstadoArticulo.DISPONIBLE);
        articuloRep.save(articulo);

        auditoriaServ.registrar(
                idAdministrador,
                "ARTICULO_APROBADO_CON_PRODUCTO",
                "Artículo " + idArticulo + " aprobado con producto " + idProducto
        );
    }

    // MÉTODO PARA OBTENER PRODUCTOS PENDIENTES DE APROBACIÓN
    public List<Producto> obtenerProductosPendientes() {
        return productoRep.findByEstado(EstadoProducto.PENDIENTE);
    }

    // MÉTODO PARA OBTENER ARTÍCULOS PENDIENTES
    public List<Articulo> obtenerArticulosPendientes() {
        return articuloRep.findByEstado(EstadoArticulo.PENDIENTE);
    }

    // MÉTODO PARA OBTENER PRODUCTOS APROBADOS
    public List<Producto> obtenerProductosAprobados() {
        return productoRep.findByEstado(EstadoProducto.APROBADO);
    }

    // MÉTODO PARA OBTENER ARTÍCULOS DISPONIBLES
    public List<Articulo> obtenerArticulosDisponibles() {
        return articuloRep.findByEstado(EstadoArticulo.DISPONIBLE);
    }
}