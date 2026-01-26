package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Articulo;
import com.alberto.laesquina.repositorio.ArticuloRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotServ {

    private final ArticuloRep articuloRep;

    public void validarSlotsPorNombre(Long idVendedor, String categoria, int limiteSlots) {
        // Usar el método que ahora SÍ funciona
        int activos = articuloRep.countByVendedorAndCategoria(idVendedor, categoria);

        if (activos >= limiteSlots) {
            throw new RuntimeException(
                    String.format(
                            "Límite de slots alcanzado para la categoría '%s'. " +
                                    "El vendedor ya tiene %d artículos activos (límite: %d).",
                            categoria, activos, limiteSlots
                    )
            );
        }
    }

    // Método alternativo si prefieres usar estado
    public void validarSlotsPorEstado(Long idVendedor, String categoria, int limiteSlots) {
        int activos = articuloRep.countActivosPorEstado(idVendedor, categoria);

        if (activos >= limiteSlots) {
            throw new RuntimeException(
                    String.format(
                            "Límite de slots alcanzado para la categoría '%s'. " +
                                    "El vendedor ya tiene %d artículos activos (límite: %d).",
                            categoria, activos, limiteSlots
                    )
            );
        }
    }

    // Mantén el método original para compatibilidad
    public void validarSlots(Long idVendedor, Long idCategoria, int limiteSlots) {
        // Convertir ID a nombre de categoría temporalmente
        String categoria = "Categoria-" + idCategoria;
        validarSlotsPorNombre(idVendedor, categoria, limiteSlots);
    }

    public void liberarSlot(Long articuloId) {
        Articulo articulo = articuloRep.findById(articuloId)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

        articulo.setActivo(false);
        articuloRep.save(articulo);
    }
}