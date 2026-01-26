package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.CompraItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraItemRep extends JpaRepository<CompraItem, Long> {
}
