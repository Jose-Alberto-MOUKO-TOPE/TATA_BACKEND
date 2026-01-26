package com.alberto.laesquina.controlador;

import com.alberto.laesquina.entidad.Carrito;
import com.alberto.laesquina.servicio.CarritoServ;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoCtrl {

    private final CarritoServ carritoServ;

    @PostMapping("/cliente/{idCliente}")
    public ResponseEntity<Carrito> crearCarrito(@PathVariable Long idCliente) {
        Carrito carrito = carritoServ.crearCarritoParaCliente(idCliente);
        return ResponseEntity.ok(carrito);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<Carrito> obtenerCarritoCliente(@PathVariable Long idCliente) {
        Carrito carrito = carritoServ.obtenerCarritoPorCliente(idCliente);
        return ResponseEntity.ok(carrito);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long id) {
        Carrito carrito = carritoServ.obtenerCarritoPorId(id);
        return ResponseEntity.ok(carrito);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarCarrito(@PathVariable Long id) {
        carritoServ.desactivarCarrito(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarCarrito(@PathVariable Long id) {
        carritoServ.activarCarrito(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoServ.eliminarCarrito(id);
        return ResponseEntity.ok().build();
    }
}