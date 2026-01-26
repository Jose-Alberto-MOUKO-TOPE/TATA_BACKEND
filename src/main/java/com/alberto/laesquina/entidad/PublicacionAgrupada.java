package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "publicaciones_agrupadas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PublicacionAgrupada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPubAgrupada;

    @ManyToOne(optional = false)
    private Producto producto;

    @Enumerated(EnumType.STRING)
    private CondicionArticulo condicion;

    private Integer stockTotal;
    private Double precioMin;
    private Double precioMax;
}

