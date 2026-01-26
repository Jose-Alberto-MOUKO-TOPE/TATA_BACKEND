package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMPRAS")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    @ManyToOne(optional = false)
    private Cliente cliente;

    private Double total;

    private LocalDateTime fecha = LocalDateTime.now();
}

