package com.tata.ecommerce.repository;

import com.tata.ecommerce.entity.Categoria;
import com.tata.ecommerce.entity.enums.EstadoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByEstado(EstadoCategoria estado);
    Optional<Categoria> findByNombre(String nombre);
    List<Categoria> findByVendedorSolicitanteId(Long vendedorId);
}
