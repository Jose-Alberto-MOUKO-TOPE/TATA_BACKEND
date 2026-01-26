package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRep extends JpaRepository<Venta, Long> {
    List<Venta> findByVendedor_IdVendedor(Long idVendedor);  // Corregido
}