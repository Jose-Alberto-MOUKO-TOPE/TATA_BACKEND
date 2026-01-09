package com.tata.ecommerce.repository;

import com.tata.ecommerce.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Optional<Vendedor> findByUsuarioId(Long usuarioId);
}
