package com.alberto.laesquina.repositorio;

import com.alberto.laesquina.entidad.Articulo;
import com.alberto.laesquina.entidad.EstadoArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRep extends JpaRepository<Articulo, Long> {

    // 1. Buscar artículos por vendedor
    List<Articulo> findByVendedor_IdVendedor(Long idVendedor);

    // 2. Contar artículos activos por vendedor y categoría - CORREGIDO
    // Usando el campo 'activo' que AHORA SÍ existe en Articulo
    @Query("SELECT COUNT(a) FROM Articulo a WHERE " +
            "a.vendedor.idVendedor = :idVendedor AND " +
            "a.publicacionAgrupada.producto.categoria = :categoria AND " +
            "a.activo = true")  // <-- 'activo' ahora existe
    int countByVendedorAndCategoria(
            @Param("idVendedor") Long idVendedor,
            @Param("categoria") String categoria);

    // 3. Buscar artículos por publicación agrupada
    List<Articulo> findByPublicacionAgrupada_IdPubAgrupada(Long idPublicacion);

    // 4. Buscar artículos por estado
    List<Articulo> findByEstado(EstadoArticulo estado);

    // 5. Buscar artículos por vendedor y estado
    List<Articulo> findByVendedor_IdVendedorAndEstado(Long idVendedor, EstadoArticulo estado);

    // 6. Contar artículos activos por vendedor
    @Query("SELECT COUNT(a) FROM Articulo a WHERE a.vendedor.idVendedor = :idVendedor AND a.activo = true")
    int countActivosByVendedor(@Param("idVendedor") Long idVendedor);

    // 7. Método ALTERNATIVO si prefieres usar estado en lugar de campo activo
    @Query("SELECT COUNT(a) FROM Articulo a WHERE " +
            "a.vendedor.idVendedor = :idVendedor AND " +
            "a.publicacionAgrupada.producto.categoria = :categoria AND " +
            "a.estado IN ('DISPONIBLE', 'RESERVADO')")  // Estados considerados activos
    int countActivosPorEstado(
            @Param("idVendedor") Long idVendedor,
            @Param("categoria") String categoria);

    long countByIdVendedorAndPublicacionProductoidCategoria(Long idVendedor, Long idCategoria);
}