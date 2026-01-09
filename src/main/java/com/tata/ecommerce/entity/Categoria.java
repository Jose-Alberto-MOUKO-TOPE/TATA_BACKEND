package com.tata.ecommerce.entity;

import com.tata.ecommerce.entity.enums.EstadoCategoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @Column(length = 2000)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCategoria estado = EstadoCategoria.PENDIENTE;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;
    
    @Column(name = "admin_aprobador_id")
    private Long adminAprobadorId;
    
    @Column(name = "vendedor_solicitante_id")
    private Long vendedorSolicitanteId;
}
