package com.tata.ecommerce.repository;

import com.tata.ecommerce.entity.Producto;
import com.tata.ecommerce.entity.enums.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEstado(EstadoProducto estado);
    List<Producto> findByCategoriaId(Long categoriaId);
    Optional<Producto> findByNombre(String nombre);
    List<Producto> findByVendedorSolicitanteId(Long vendedorId);
}
