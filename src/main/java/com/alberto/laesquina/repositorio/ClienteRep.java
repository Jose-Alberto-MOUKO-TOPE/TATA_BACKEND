package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRep extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByIdUsuario(Long idUsuario);
}

