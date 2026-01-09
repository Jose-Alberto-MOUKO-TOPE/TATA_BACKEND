package com.tata.ecommerce.repository;

import com.tata.ecommerce.entity.PublicacionAgrupada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicacionAgrupadaRepository extends JpaRepository<PublicacionAgrupada, Long> {
    Optional<PublicacionAgrupada> findByProductoId(Long productoId);
    List<PublicacionAgrupada> findByCategoriaId(Long categoriaId);
}
