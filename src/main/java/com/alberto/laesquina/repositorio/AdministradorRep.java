package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRep extends JpaRepository<Administrador, Long> {

    Optional<Administrador> findByIdUsuario(Long idUsuario);
}

