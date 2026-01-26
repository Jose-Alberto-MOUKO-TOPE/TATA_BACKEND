package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "compra_items")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CompraItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompraItem;

    @ManyToOne(optional = false)
    private Compra compra;

    @ManyToOne(optional = false)
    private Articulo articulo;

    private Double precioUnitario;
    private Integer cantidad;
}

