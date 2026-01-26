package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendedorRep extends JpaRepository<Vendedor, Long> {

    Optional<Vendedor> findByIdUsuario(Long idUsuario);
}

