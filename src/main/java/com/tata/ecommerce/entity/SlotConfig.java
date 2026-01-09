package com.tata.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "slots_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotConfig {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;
    
    @Column(name = "slots_maximos", nullable = false)
    private Integer slotsMaximos;
    
    @Column(name = "slots_utilizados", nullable = false)
    private Integer slotsUtilizados = 0;
    
    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion = LocalDateTime.now();
    
    @Column(name = "admin_asignador_id")
    private Long adminAsignadorId;
}
