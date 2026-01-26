package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRep extends JpaRepository<Auditoria,Long> {
}
