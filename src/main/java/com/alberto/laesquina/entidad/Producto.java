package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCTOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    private String nombre;
    private String categoria;

    @Enumerated(EnumType.STRING)
    private EstadoProducto estado;

    @ManyToOne
    @JoinColumn(name = "admin_aprobador_id")
    private Administrador administradorAprobador;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaRevision;
}

