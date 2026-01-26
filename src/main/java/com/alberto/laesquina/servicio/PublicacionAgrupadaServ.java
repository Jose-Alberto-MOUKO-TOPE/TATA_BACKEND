package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.PublicacionAgrupada;
import com.alberto.laesquina.entidad.Producto;
import com.alberto.laesquina.entidad.CondicionArticulo;
import com.alberto.laesquina.repositorio.PublicacionAgrupadaRep;
import com.alberto.laesquina.repositorio.ProductoRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PublicacionAgrupadaServ {

    private final PublicacionAgrupadaRep publicacionAgrupadaRep;
    private final ProductoRep productoRep;

    public PublicacionAgrupada crearPublicacionAgrupada(PublicacionAgrupada publicacion) {
        // Validar que el producto existe
        Producto producto = productoRep.findById(publicacion.getProducto().getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        publicacion.setProducto(producto);

        // Validar stock total
        if (publicacion.getStockTotal() == null || publicacion.getStockTotal() <= 0) {
            throw new RuntimeException("El stock total debe ser mayor a 0");
        }

        // Validar precios
        if (publicacion.getPrecioMin() == null || publicacion.getPrecioMax() == null) {
            throw new RuntimeException("Debe especificar precio mínimo y máximo");
        }

        if (publicacion.getPrecioMin() <= 0 || publicacion.getPrecioMax() <= 0) {
            throw new RuntimeException("Los precios deben ser mayores a 0");
        }

        if (publicacion.getPrecioMin() > publicacion.getPrecioMax()) {
            throw new RuntimeException("El precio mínimo no puede ser mayor al precio máximo");
        }

        return publicacionAgrupadaRep.save(publicacion);
    }

    public PublicacionAgrupada actualizarPublicacion(Long id, PublicacionAgrupada actualizada) {
        PublicacionAgrupada existente = obtenerPorId(id);

        // Validar stock
        if (actualizada.getStockTotal() != null && actualizada.getStockTotal() <= 0) {
            throw new RuntimeException("El stock total debe ser mayor a 0");
        }

        // Validar precios
        if (actualizada.getPrecioMin() != null && actualizada.getPrecioMax() != null) {
            if (actualizada.getPrecioMin() <= 0 || actualizada.getPrecioMax() <= 0) {
                throw new RuntimeException("Los precios deben ser mayores a 0");
            }

            if (actualizada.getPrecioMin() > actualizada.getPrecioMax()) {
                throw new RuntimeException("El precio mínimo no puede ser mayor al precio máximo");
            }
        }

        // Actualizar campos
        if (actualizada.getCondicion() != null) {
            existente.setCondicion(actualizada.getCondicion());
        }

        if (actualizada.getStockTotal() != null) {
            existente.setStockTotal(actualizada.getStockTotal());
        }

        if (actualizada.getPrecioMin() != null) {
            existente.setPrecioMin(actualizada.getPrecioMin());
        }

        if (actualizada.getPrecioMax() != null) {
            existente.setPrecioMax(actualizada.getPrecioMax());
        }

        return publicacionAgrupadaRep.save(existente);
    }

    public PublicacionAgrupada obtenerPorId(Long id) {
        return publicacionAgrupadaRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación agrupada no encontrada"));
    }

    public List<PublicacionAgrupada> obtenerPorCategoria(Long idCategoria) {
        return publicacionAgrupadaRep.findByProductoIdCategoria(idCategoria);
    }

    public List<PublicacionAgrupada> obtenerPorProducto(Long idProducto) {
        return publicacionAgrupadaRep.findAll().stream()
                .filter(pa -> pa.getProducto().getIdProducto().equals(idProducto))
                .toList();
    }

    public List<PublicacionAgrupada> obtenerPorCondicion(CondicionArticulo condicion) {
        return publicacionAgrupadaRep.findAll().stream()
                .filter(pa -> pa.getCondicion() == condicion)
                .toList();
    }

    public void reducirStock(Long id, Integer cantidad) {
        PublicacionAgrupada publicacion = obtenerPorId(id);

        if (publicacion.getStockTotal() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        publicacion.setStockTotal(publicacion.getStockTotal() - cantidad);
        publicacionAgrupadaRep.save(publicacion);
    }

    public void aumentarStock(Long id, Integer cantidad) {
        PublicacionAgrupada publicacion = obtenerPorId(id);
        publicacion.setStockTotal(publicacion.getStockTotal() + cantidad);
        publicacionAgrupadaRep.save(publicacion);
    }

    public void eliminarPublicacion(Long id) {
        publicacionAgrupadaRep.deleteById(id);
    }
}