package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "VENTAS")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @ManyToOne(optional = false)
    private Vendedor vendedor;

    @ManyToOne(optional = false)
    private Articulo articulo;

    private Double precio;
    private LocalDateTime fecha = LocalDateTime.now();
}

