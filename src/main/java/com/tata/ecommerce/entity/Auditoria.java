package com.tata.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auditoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "actor_id", nullable = false)
    private Long actorId;
    
    @Column(nullable = false)
    private String accion;
    
    @Column(length = 2000)
    private String descripcion;
    
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}
