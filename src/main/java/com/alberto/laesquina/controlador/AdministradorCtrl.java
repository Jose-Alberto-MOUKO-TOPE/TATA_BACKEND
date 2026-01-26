package com.alberto.laesquina.controlador;

import com.alberto.laesquina.servicio.AdministradorServ;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdministradorCtrl {

    private final AdministradorServ administradorServ;

    // Endpoint para aprobar PRODUCTOS (no artículos)
    @PostMapping("/productos/{id}/aprobar")
    public ResponseEntity<?> aprobarProducto(
            @PathVariable Long id,
            @RequestBody Map<String, Long> request) {
        try {
            Long adminId = request.get("adminId");
            administradorServ.aprobarProducto(id, adminId);
            return ResponseEntity.ok(Map.of("mensaje", "Producto aprobado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint para rechazar PRODUCTOS
    @PostMapping("/productos/{id}/rechazar")
    public ResponseEntity<?> rechazarProducto(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        try {
            Long adminId = ((Number) request.get("adminId")).longValue();
            String motivo = (String) request.get("motivo");
            administradorServ.rechazarProducto(id, adminId, motivo);
            return ResponseEntity.ok(Map.of("mensaje", "Producto rechazado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint para aprobar ARTÍCULOS (separado)
    @PostMapping("/articulos/{id}/aprobar")
    public ResponseEntity<?> aprobarArticulo(
            @PathVariable Long id,
            @RequestBody Map<String, Long> request) {
        try {
            Long adminId = request.get("adminId");
            administradorServ.aprobarArticulo(id, adminId);
            return ResponseEntity.ok(Map.of("mensaje", "Artículo aprobado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}