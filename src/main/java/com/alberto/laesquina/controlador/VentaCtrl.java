package com.alberto.laesquina.controlador;

import com.alberto.laesquina.entidad.Venta;
import com.alberto.laesquina.servicio.VentaServ;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaCtrl {

    private final VentaServ ventaServ;

    @PostMapping
    public ResponseEntity<Venta> registrarVenta(@RequestParam Long idVendedor,
                                                @RequestParam Long idArticulo,
                                                @RequestParam Double precio) {
        Venta venta = ventaServ.registrarVenta(idVendedor, idArticulo, precio);
        return ResponseEntity.ok(venta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Long id) {
        Venta venta = ventaServ.obtenerPorId(id);
        return ResponseEntity.ok(venta);
    }

    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<Venta>> obtenerVentasVendedor(@PathVariable Long idVendedor) {
        List<Venta> ventas = ventaServ.obtenerPorVendedor(idVendedor);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodas() {
        List<Venta> ventas = ventaServ.obtenerTodas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Venta>> obtenerPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<Venta> ventas = ventaServ.obtenerPorRangoFechas(inicio, fin);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/vendedor/{idVendedor}/total")
    public ResponseEntity<Double> obtenerTotalVentasVendedor(@PathVariable Long idVendedor) {
        Double total = ventaServ.obtenerTotalVentasVendedor(idVendedor);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/vendedor/{idVendedor}/cantidad")
    public ResponseEntity<Long> obtenerCantidadVentasVendedor(@PathVariable Long idVendedor) {
        Long cantidad = ventaServ.obtenerCantidadVentasVendedor(idVendedor);
        return ResponseEntity.ok(cantidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaServ.eliminarVenta(id);
        return ResponseEntity.ok().build();
    }
}