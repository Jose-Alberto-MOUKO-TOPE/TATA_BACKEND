package com.tata.ecommerce.controller;

import com.tata.ecommerce.entity.SlotConfig;
import com.tata.ecommerce.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/slots")
public class SlotController {
    
    @Autowired
    private SlotService slotService;
    
    @PostMapping("/asignar")
    public ResponseEntity<SlotConfig> asignarSlots(
        @RequestParam Long vendedorId,
        @RequestParam Long categoriaId,
        @RequestParam Integer cantidadSlots,
        @RequestParam Long adminId
    ) {
        return ResponseEntity.ok(slotService.asignarSlots(vendedorId, categoriaId, cantidadSlots, adminId));
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<Map<String, Object>> consultarSlotsDisponibles(
        @RequestParam Long vendedorId,
        @RequestParam Long categoriaId
    ) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("tieneDisponible", slotService.tieneSlotDisponible(vendedorId, categoriaId));
        respuesta.put("slotsDisponibles", slotService.obtenerSlotsDisponibles(vendedorId, categoriaId));
        respuesta.put("config", slotService.obtenerSlotConfig(vendedorId, categoriaId));
        return ResponseEntity.ok(respuesta);
    }
    
    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<SlotConfig>> obtenerSlotsPorVendedor(@PathVariable Long vendedorId) {
        return ResponseEntity.ok(slotService.obtenerSlotsPorVendedor(vendedorId));
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<SlotConfig>> obtenerSlotsPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(slotService.obtenerSlotsPorCategoria(categoriaId));
    }
}
