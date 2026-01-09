package com.tata.ecommerce.entity;

import com.tata.ecommerce.entity.enums.EstadoProducto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(length = 2000)
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    private String marca;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProducto estado = EstadoProducto.PENDIENTE;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;
    
    @Column(name = "admin_aprobador_id")
    private Long adminAprobadorId;
    
    @Column(name = "vendedor_solicitante_id")
    private Long vendedorSolicitanteId;
}
