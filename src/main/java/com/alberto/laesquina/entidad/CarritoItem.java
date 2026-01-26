package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito_items")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarritoItem;

    @ManyToOne(optional = false)
    private Carrito carrito;

    @ManyToOne(optional = false)
    private Articulo articulo;

    private Integer cantidad;
}

