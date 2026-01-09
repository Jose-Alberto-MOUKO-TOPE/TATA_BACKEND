package com.tata.ecommerce.repository;

import com.tata.ecommerce.entity.SlotConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlotConfigRepository extends JpaRepository<SlotConfig, Long> {
    Optional<SlotConfig> findByVendedorIdAndCategoriaId(Long vendedorId, Long categoriaId);
    List<SlotConfig> findByVendedorId(Long vendedorId);
    List<SlotConfig> findByCategoriaId(Long categoriaId);
}
