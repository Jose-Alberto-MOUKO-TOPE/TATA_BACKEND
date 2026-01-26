package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Articulo;
import com.alberto.laesquina.entidad.EstadoArticulo;
import com.alberto.laesquina.entidad.PublicacionAgrupada;
import com.alberto.laesquina.entidad.Producto;
import com.alberto.laesquina.repositorio.ArticuloRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticuloServ {

    private final ArticuloRep articuloRep;
    private final SlotServ slotServ;

    public Articulo publicarArticulo(Articulo articulo, int limiteSlots) {
        // Validar que el artículo tenga vendedor
        if (articulo.getVendedor() == null) {
            throw new RuntimeException("El artículo debe tener un vendedor asignado");
        }

        // Validar que el artículo tenga publicación agrupada
        if (articulo.getPublicacionAgrupada() == null) {
            throw new RuntimeException("El artículo debe tener una publicación agrupada");
        }

        // Validar que la publicación agrupada tenga producto
        PublicacionAgrupada publicacionAgrupada = articulo.getPublicacionAgrupada();
        if (publicacionAgrupada.getProducto() == null) {
            throw new RuntimeException("La publicación agrupada debe tener un producto");
        }

        // Obtener categoría del producto (String)
        Producto producto = publicacionAgrupada.getProducto();
        String categoria = producto.getCategoria();

        if (categoria == null || categoria.trim().isEmpty()) {
            throw new RuntimeException("El producto debe tener una categoría asignada");
        }

        // Validar slots
        slotServ.validarSlotsPorNombre(
                articulo.getVendedor().getIdVendedor(),
                categoria,
                limiteSlots
        );

        // Configurar el artículo
        articulo.setEstado(EstadoArticulo.DISPONIBLE);
        articulo.setActivo(true);  // <-- AHORA este método funciona

        return articuloRep.save(articulo);
    }

    public void marcarComoVendido(Long articuloId) {
        Articulo articulo = articuloRep.findById(articuloId)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

        articulo.setEstado(EstadoArticulo.VENDIDO);
        articulo.setActivo(false);  // <-- Artículo vendido ya no está activo

        articuloRep.save(articulo);
    }

    public void reservar(Long articuloId) {
        Articulo articulo = articuloRep.findById(articuloId)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

        if (articulo.getEstado() != EstadoArticulo.DISPONIBLE) {
            throw new RuntimeException("Artículo no disponible. Estado actual: " + articulo.getEstado());
        }

        articulo.setEstado(EstadoArticulo.RESERVADO);
        // El artículo sigue activo cuando está reservado
        articulo.setActivo(true);

        articuloRep.save(articulo);
    }
}