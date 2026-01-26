package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.PublicacionAgrupada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionAgrupadaRep extends JpaRepository<PublicacionAgrupada, Long> {

    List<PublicacionAgrupada> findByProductoIdCategoria(Long idCategoria);
}

