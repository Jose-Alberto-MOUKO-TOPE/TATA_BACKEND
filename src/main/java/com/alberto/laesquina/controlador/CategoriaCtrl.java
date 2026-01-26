package com.alberto.laesquina.controlador;

import com.alberto.laesquina.entidad.Categoria;
import com.alberto.laesquina.servicio.CategoriaServ;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaCtrl {

    private final CategoriaServ categoriaServ;

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nueva = categoriaServ.crearCategoria(categoria);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id,
                                                         @RequestBody Categoria categoria) {
        Categoria actualizada = categoriaServ.actualizarCategoria(id, categoria);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable Long id) {
        Categoria categoria = categoriaServ.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Categoria> obtenerPorNombre(@PathVariable String nombre) {
        return categoriaServ.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        List<Categoria> categorias = categoriaServ.obtenerTodas();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Categoria>> obtenerActivas() {
        List<Categoria> categorias = categoriaServ.obtenerActivas();
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable Long id) {
        categoriaServ.desactivarCategoria(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarCategoria(@PathVariable Long id) {
        categoriaServ.activarCategoria(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaServ.eliminarCategoria(id);
        return ResponseEntity.ok().build();
    }
}