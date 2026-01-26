package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TESTIMONIOS")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Testimonio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario autor;

    @ManyToOne(optional = false)
    private Vendedor vendedor;

    private String comentario;
    private Integer calificacion;
    private LocalDateTime fecha = LocalDateTime.now();
}

