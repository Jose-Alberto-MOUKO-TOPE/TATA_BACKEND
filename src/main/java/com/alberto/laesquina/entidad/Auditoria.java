package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "AUDITORIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAuditoria;

    @ManyToOne(optional = false)
    private Usuario usuario;

    private String accion;
    private String entidad;
    private String descripcion;
    private LocalDateTime fecha = LocalDateTime.now();

    public void setActorId(Long actorId) {
    }

    public void setTipoAccion(String tipo) {

    }
}

