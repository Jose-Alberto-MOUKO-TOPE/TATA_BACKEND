package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Categoria;
import com.alberto.laesquina.repositorio.CategoriaRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaServ {

    private final CategoriaRep categoriaRep;

    public Categoria crearCategoria(Categoria categoria) {
        // Verificar si ya existe una categoría con ese nombre
        Optional<Categoria> existente = categoriaRep.findByNombre(categoria.getNombre());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        categoria.setActiva(true);
        return categoriaRep.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria categoriaActualizada) {
        Categoria categoria = obtenerPorId(id);

        // Verificar si el nuevo nombre ya existe (excluyendo la actual)
        if (!categoria.getNombre().equals(categoriaActualizada.getNombre())) {
            Optional<Categoria> existente = categoriaRep.findByNombre(categoriaActualizada.getNombre());
            if (existente.isPresent() && !existente.get().getIdCategoria().equals(id)) {
                throw new RuntimeException("Ya existe otra categoría con ese nombre");
            }
        }

        categoria.setNombre(categoriaActualizada.getNombre());
        categoria.setDescripcion(categoriaActualizada.getDescripcion());

        return categoriaRep.save(categoria);
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Optional<Categoria> obtenerPorNombre(String nombre) {
        return categoriaRep.findByNombre(nombre);
    }

    public List<Categoria> obtenerTodas() {
        return categoriaRep.findAll();
    }

    public List<Categoria> obtenerActivas() {
        return categoriaRep.findByActivaTrue();
    }

    public void desactivarCategoria(Long id) {
        Categoria categoria = obtenerPorId(id);
        categoria.setActiva(false);
        categoriaRep.save(categoria);
    }

    public void activarCategoria(Long id) {
        Categoria categoria = obtenerPorId(id);
        categoria.setActiva(true);
        categoriaRep.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        categoriaRep.deleteById(id);
    }
}