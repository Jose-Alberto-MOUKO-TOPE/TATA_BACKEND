package com.tata.ecommerce.entity;

import com.tata.ecommerce.entity.enums.EstadoArticulo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "articulos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;
    
    @ManyToOne
    @JoinColumn(name = "publicacion_agrupada_id")
    private PublicacionAgrupada publicacionAgrupada;
    
    @Column(nullable = false)
    private BigDecimal precio;
    
    @Column(nullable = false)
    private Integer stock;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoArticulo estado = EstadoArticulo.DISPONIBLE;
    
    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDateTime fechaPublicacion = LocalDateTime.now();
    
    private String condicion;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
}
