package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Vendedor;
import com.alberto.laesquina.repositorio.ArticuloRep;
import com.alberto.laesquina.repositorio.VendedorRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendedorServ {

    private final VendedorRep vendedorRep;
    private final ArticuloRep articuloRep;

    private static final int LIMITE_SLOTS = 3;

    public Vendedor obtenerPorUsuario(Long idUsuario) {
        return vendedorRep.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
    }

    public void validarSlots(Long idVendedor, Long idCategoria) {
        long usados = articuloRep
                .countByIdVendedorAndPublicacionProductoidCategoria(idVendedor, idCategoria);

        if (usados >= LIMITE_SLOTS) {
            throw new RuntimeException(
                    "Has alcanzado el límite de publicaciones para esta categoría"
            );
        }
    }
}

