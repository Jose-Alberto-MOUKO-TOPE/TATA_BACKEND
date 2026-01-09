package com.tata.ecommerce.repository;

import com.tata.ecommerce.entity.Articulo;
import com.tata.ecommerce.entity.enums.EstadoArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    List<Articulo> findByVendedorId(Long vendedorId);
    List<Articulo> findByProductoId(Long productoId);
    List<Articulo> findByEstado(EstadoArticulo estado);
    List<Articulo> findByProductoIdAndEstadoIn(Long productoId, List<EstadoArticulo> estados);
    long countByVendedorIdAndProductoCategoriaIdAndEstadoIn(Long vendedorId, Long categoriaId, List<EstadoArticulo> estados);
    List<Articulo> findByPublicacionAgrupadaId(Long publicacionAgrupadaId);
}
