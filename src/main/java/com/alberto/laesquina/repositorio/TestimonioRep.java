package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Testimonio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonioRep extends JpaRepository<Testimonio, Long> {

    List<Testimonio> findByAutorId(Long idUsuario);
}

