package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Auditoria;
import com.alberto.laesquina.repositorio.AuditoriaRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditoriaServ {

    private final AuditoriaRep auditoriaRep;

    public void registrar(Long actorId, String tipo, String descripcion) {
        Auditoria audit = new Auditoria();
        audit.setActorId(actorId);
        audit.setTipoAccion(tipo);
        audit.setDescripcion(descripcion);
        audit.setFecha(LocalDateTime.now());

        auditoriaRep.save(audit);
    }
}

