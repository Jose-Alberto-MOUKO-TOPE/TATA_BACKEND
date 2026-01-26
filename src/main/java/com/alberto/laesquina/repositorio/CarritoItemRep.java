package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoItemRep extends JpaRepository<CarritoItem, Long> {

    Optional<CarritoItem> findByidCarritoAndidArticulo(Long idCarrito, Long idArticulo);
}

