package com.tata.ecommerce.repository;

import com.tata.ecommerce.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByActorId(Long actorId);
    List<Auditoria> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
