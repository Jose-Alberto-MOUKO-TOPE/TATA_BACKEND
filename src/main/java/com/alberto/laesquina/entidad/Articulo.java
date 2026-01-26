package com.alberto.laesquina.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ARTICULOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_publicacion_agrupada")
    private PublicacionAgrupada publicacionAgrupada;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;

    private Double precio;
    private String color;
    private String almacenamiento;
    private Boolean parteRota;
    private Boolean golpes;
    private Boolean funcionaCorrectamente;

    @Enumerated(EnumType.STRING)
    private EstadoArticulo estado = EstadoArticulo.DISPONIBLE;

    // AÑADE ESTE CAMPO si quieres un campo 'activo' separado
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // CORRIGE ESTE MÉTODO - no debería existir o debería retornar Producto
    // Si necesitas obtener el producto, hazlo a través de publicacionAgrupada
    public Producto getProducto() {
        if (this.publicacionAgrupada != null && this.publicacionAgrupada.getProducto() != null) {
            return this.publicacionAgrupada.getProducto();
        }
        return null;
    }

    // Método setActivo CORREGIDO
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    // Método getActivo
    public Boolean getActivo() {
        return this.activo != null ? this.activo : true; // Por defecto true
    }
}