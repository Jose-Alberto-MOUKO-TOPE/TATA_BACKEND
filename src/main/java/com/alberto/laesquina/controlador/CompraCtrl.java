package com.alberto.laesquina.controlador;

import com.alberto.laesquina.entidad.Compra;
import com.alberto.laesquina.servicio.CompraServ;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraCtrl {

    private final CompraServ compraServ;

    @PostMapping("/cliente/{idCliente}")
    public ResponseEntity<Compra> crearCompra(@PathVariable Long idCliente,
                                              @RequestParam Double total) {
        Compra compra = compraServ.crearCompra(idCliente, total);
        return ResponseEntity.ok(compra);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerCompra(@PathVariable Long id) {
        Compra compra = compraServ.obtenerPorId(id);
        return ResponseEntity.ok(compra);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Compra>> obtenerComprasCliente(@PathVariable Long idCliente) {
        List<Compra> compras = compraServ.obtenerPorCliente(idCliente);
        return ResponseEntity.ok(compras);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> obtenerTodas() {
        List<Compra> compras = compraServ.obtenerTodas();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Compra>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<Compra> compras = compraServ.obtenerPorRangoFechas(inicio, fin);
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/cliente/{idCliente}/total")
    public ResponseEntity<Double> obtenerTotalComprasCliente(@PathVariable Long idCliente) {
        Double total = compraServ.obtenerTotalComprasCliente(idCliente);
        return ResponseEntity.ok(total);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable Long id) {
        compraServ.eliminarCompra(id);
        return ResponseEntity.ok().build();
    }
}