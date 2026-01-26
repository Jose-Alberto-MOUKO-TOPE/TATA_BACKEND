package com.alberto.laesquina.controlador;

import com.alberto.laesquina.entidad.Testimonio;
import com.alberto.laesquina.servicio.TestimonioServ;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonios")
@RequiredArgsConstructor
public class TestimonioCtrl {

    private final TestimonioServ testimonioServ;

    @PostMapping
    public ResponseEntity<Testimonio> crearTestimonio(
            @RequestParam Long idAutor,
            @RequestParam Long idVendedor,
            @RequestParam String comentario,
            @RequestParam Integer calificacion) {
        Testimonio testimonio = testimonioServ.crearTestimonio(idAutor, idVendedor, comentario, calificacion);
        return ResponseEntity.ok(testimonio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Testimonio> obtenerTestimonio(@PathVariable Long id) {
        Testimonio testimonio = testimonioServ.obtenerPorId(id);
        return ResponseEntity.ok(testimonio);
    }

    @GetMapping("/autor/{idUsuario}")
    public ResponseEntity<List<Testimonio>> obtenerPorAutor(@PathVariable Long idUsuario) {
        List<Testimonio> testimonios = testimonioServ.obtenerPorAutor(idUsuario);
        return ResponseEntity.ok(testimonios);
    }

    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<Testimonio>> obtenerPorVendedor(@PathVariable Long idVendedor) {
        List<Testimonio> testimonios = testimonioServ.obtenerPorVendedor(idVendedor);
        return ResponseEntity.ok(testimonios);
    }

    @GetMapping
    public ResponseEntity<List<Testimonio>> obtenerTodos() {
        List<Testimonio> testimonios = testimonioServ.obtenerTodos();
        return ResponseEntity.ok(testimonios);
    }

    @GetMapping("/vendedor/{idVendedor}/calificacion-promedio")
    public ResponseEntity<Double> obtenerCalificacionPromedio(@PathVariable Long idVendedor) {
        Double promedio = testimonioServ.obtenerCalificacionPromedioVendedor(idVendedor);
        return ResponseEntity.ok(promedio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Testimonio> actualizarTestimonio(@PathVariable Long id,
                                                           @RequestParam(required = false) String comentario,
                                                           @RequestParam(required = false) Integer calificacion) {
        Testimonio actualizado = testimonioServ.actualizarTestimonio(id, comentario, calificacion);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTestimonio(@PathVariable Long id) {
        testimonioServ.eliminarTestimonio(id);
        return ResponseEntity.ok().build();
    }
}