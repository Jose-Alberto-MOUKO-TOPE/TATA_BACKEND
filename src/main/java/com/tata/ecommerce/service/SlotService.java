package com.tata.ecommerce.service;

import com.tata.ecommerce.entity.Categoria;
import com.tata.ecommerce.entity.SlotConfig;
import com.tata.ecommerce.entity.Vendedor;
import com.tata.ecommerce.entity.enums.EstadoArticulo;
import com.tata.ecommerce.repository.ArticuloRepository;
import com.tata.ecommerce.repository.SlotConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SlotService {
    
    @Autowired
    private SlotConfigRepository slotConfigRepository;
    
    @Autowired
    private ArticuloRepository articuloRepository;
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @Transactional
    public SlotConfig asignarSlots(Long vendedorId, Long categoriaId, Integer cantidadSlots, Long adminId) {
        SlotConfig slotConfig = slotConfigRepository.findByVendedorIdAndCategoriaId(vendedorId, categoriaId)
            .orElse(new SlotConfig());
        
        slotConfig.setVendedor(new Vendedor());
        slotConfig.getVendedor().setId(vendedorId);
        slotConfig.setCategoria(new Categoria());
        slotConfig.getCategoria().setId(categoriaId);
        slotConfig.setSlotsMaximos(cantidadSlots);
        slotConfig.setFechaAsignacion(LocalDateTime.now());
        slotConfig.setAdminAsignadorId(adminId);
        
        SlotConfig guardado = slotConfigRepository.save(slotConfig);
        
        auditoriaService.registrar(
            "ASIGNACION_SLOTS",
            "Admin " + adminId + " asignó " + cantidadSlots + " slots a vendedor " + vendedorId + " en categoría " + categoriaId,
            null,
            adminId
        );
        
        return guardado;
    }
    
    public boolean tieneSlotDisponible(Long vendedorId, Long categoriaId) {
        SlotConfig slotConfig = slotConfigRepository.findByVendedorIdAndCategoriaId(vendedorId, categoriaId)
            .orElse(null);
        
        if (slotConfig == null) {
            return false;
        }
        
        long articulosActivos = articuloRepository.countByVendedorIdAndProductoCategoriaIdAndEstadoIn(
            vendedorId, 
            categoriaId, 
            List.of(EstadoArticulo.DISPONIBLE, EstadoArticulo.RESERVADO)
        );
        
        return articulosActivos < slotConfig.getSlotsMaximos();
    }
    
    public int obtenerSlotsDisponibles(Long vendedorId, Long categoriaId) {
        SlotConfig slotConfig = slotConfigRepository.findByVendedorIdAndCategoriaId(vendedorId, categoriaId)
            .orElse(null);
        
        if (slotConfig == null) {
            return 0;
        }
        
        long articulosActivos = articuloRepository.countByVendedorIdAndProductoCategoriaIdAndEstadoIn(
            vendedorId, 
            categoriaId, 
            List.of(EstadoArticulo.DISPONIBLE, EstadoArticulo.RESERVADO)
        );
        
        return (int) (slotConfig.getSlotsMaximos() - articulosActivos);
    }
    
    public SlotConfig obtenerSlotConfig(Long vendedorId, Long categoriaId) {
        return slotConfigRepository.findByVendedorIdAndCategoriaId(vendedorId, categoriaId)
            .orElse(null);
    }
    
    public List<SlotConfig> obtenerSlotsPorVendedor(Long vendedorId) {
        return slotConfigRepository.findByVendedorId(vendedorId);
    }
    
    public List<SlotConfig> obtenerSlotsPorCategoria(Long categoriaId) {
        return slotConfigRepository.findByCategoriaId(categoriaId);
    }
}
