package com.tata.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "publicaciones_agrupadas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicacionAgrupada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false, unique = true)
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
    @OneToMany(mappedBy = "publicacionAgrupada")
    private List<Articulo> articulos;
    
    @Column(name = "total_articulos")
    private Integer totalArticulos = 0;
    
    @Column(name = "precio_minimo")
    private java.math.BigDecimal precioMinimo;
    
    @Column(name = "precio_maximo")
    private java.math.BigDecimal precioMaximo;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
