package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRep extends JpaRepository<Carrito, Long> {

    Optional<Carrito> findByIdCliente(Long idCliente);
}

