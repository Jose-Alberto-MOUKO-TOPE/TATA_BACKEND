package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRep extends JpaRepository<Compra, Long> {
    List<Compra> findByCliente_IdCliente(Long idCliente);  // Corregido
}