// src/main/java/com/alberto/laesquina/repositorio/ProductoRep.java
package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Producto;
import com.alberto.laesquina.entidad.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("productoRep")  // <-- NOMBRE EXPLÍCITO del bean
public interface ProductoRep extends JpaRepository<Producto, Long> {

    // Método básico
    List<Producto> findByEstado(EstadoProducto estado);

    // Métodos adicionales para asegurar que funciona
    @Query("SELECT p FROM Producto p WHERE p.estado = :estado")
    List<Producto> buscarPorEstado(@Param("estado") EstadoProducto estado);

    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByCategoriaIgnoreCase(String categoria);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:keyword%")
    List<Producto> buscarPorPalabraClave(@Param("keyword") String keyword);
}