package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CARRITOS")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    @OneToOne(optional = false)
    private Cliente cliente;

    private Boolean activo = true;
}

