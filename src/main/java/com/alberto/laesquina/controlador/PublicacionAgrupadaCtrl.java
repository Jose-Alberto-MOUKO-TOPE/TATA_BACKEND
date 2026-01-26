package com.alberto.laesquina.controlador;

import com.alberto.laesquina.entidad.PublicacionAgrupada;
import com.alberto.laesquina.servicio.PublicacionAgrupadaServ;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones-agrupadas")
@RequiredArgsConstructor
public class PublicacionAgrupadaCtrl {

    private final PublicacionAgrupadaServ publicacionAgrupadaServ;

    @PostMapping
    public ResponseEntity<PublicacionAgrupada> crearPublicacion(@RequestBody PublicacionAgrupada publicacion) {
        PublicacionAgrupada nueva = publicacionAgrupadaServ.crearPublicacionAgrupada(publicacion);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionAgrupada> actualizarPublicacion(@PathVariable Long id,
                                                                     @RequestBody PublicacionAgrupada publicacion) {
        PublicacionAgrupada actualizada = publicacionAgrupadaServ.actualizarPublicacion(id, publicacion);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionAgrupada> obtenerPublicacion(@PathVariable Long id) {
        PublicacionAgrupada publicacion = publicacionAgrupadaServ.obtenerPorId(id);
        return ResponseEntity.ok(publicacion);
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<PublicacionAgrupada>> obtenerPorCategoria(@PathVariable Long idCategoria) {
        List<PublicacionAgrupada> publicaciones = publicacionAgrupadaServ.obtenerPorCategoria(idCategoria);
        return ResponseEntity.ok(publicaciones);
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<PublicacionAgrupada>> obtenerPorProducto(@PathVariable Long idProducto) {
        List<PublicacionAgrupada> publicaciones = publicacionAgrupadaServ.obtenerPorProducto(idProducto);
        return ResponseEntity.ok(publicaciones);
    }

    @PutMapping("/{id}/reducir-stock")
    public ResponseEntity<Void> reducirStock(@PathVariable Long id,
                                             @RequestParam Integer cantidad) {
        publicacionAgrupadaServ.reducirStock(id, cantidad);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/aumentar-stock")
    public ResponseEntity<Void> aumentarStock(@PathVariable Long id,
                                              @RequestParam Integer cantidad) {
        publicacionAgrupadaServ.aumentarStock(id, cantidad);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id) {
        publicacionAgrupadaServ.eliminarPublicacion(id);
        return ResponseEntity.ok().build();
    }
}