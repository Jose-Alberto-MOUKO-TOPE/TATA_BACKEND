package com.alberto.laesquina.servicio;

import com.alberto.laesquina.entidad.Testimonio;
import com.alberto.laesquina.entidad.Usuario;
import com.alberto.laesquina.entidad.Vendedor;
import com.alberto.laesquina.repositorio.TestimonioRep;
import com.alberto.laesquina.repositorio.UsuarioRep;
import com.alberto.laesquina.repositorio.VendedorRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TestimonioServ {

    private final TestimonioRep testimonioRep;
    private final UsuarioRep usuarioRep;
    private final VendedorRep vendedorRep;

    public Testimonio crearTestimonio(Long idAutor, Long idVendedor, String comentario, Integer calificacion) {
        Usuario autor = usuarioRep.findById(idAutor)
                .orElseThrow(() -> new RuntimeException("Usuario/autor no encontrado"));

        Vendedor vendedor = vendedorRep.findById(idVendedor)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        // Validar calificación (1-5)
        if (calificacion < 1 || calificacion > 5) {
            throw new RuntimeException("La calificación debe estar entre 1 y 5");
        }

        // Validar que el autor no sea el mismo vendedor
        // (Necesitarías una relación entre Usuario y Vendedor para verificar esto)

        Testimonio testimonio = new Testimonio();
        testimonio.setAutor(autor);
        testimonio.setVendedor(vendedor);
        testimonio.setComentario(comentario);
        testimonio.setCalificacion(calificacion);
        testimonio.setFecha(LocalDateTime.now());

        return testimonioRep.save(testimonio);
    }

    public Testimonio obtenerPorId(Long id) {
        return testimonioRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonio no encontrado"));
    }

    public List<Testimonio> obtenerPorAutor(Long idUsuario) {
        return testimonioRep.findByAutorId(idUsuario);
    }

    public List<Testimonio> obtenerPorVendedor(Long idVendedor) {
        return testimonioRep.findAll().stream()
                .filter(t -> t.getVendedor().getIdVendedor().equals(idVendedor))
                .toList();
    }

    public List<Testimonio> obtenerTodos() {
        return testimonioRep.findAll();
    }

    public Double obtenerCalificacionPromedioVendedor(Long idVendedor) {
        List<Testimonio> testimonios = obtenerPorVendedor(idVendedor);

        if (testimonios.isEmpty()) {
            return 0.0;
        }

        return testimonios.stream()
                .mapToInt(Testimonio::getCalificacion)
                .average()
                .orElse(0.0);
    }

    public Testimonio actualizarTestimonio(Long id, String comentario, Integer calificacion) {
        Testimonio testimonio = obtenerPorId(id);

        if (comentario != null && !comentario.trim().isEmpty()) {
            testimonio.setComentario(comentario);
        }

        if (calificacion != null) {
            if (calificacion < 1 || calificacion > 5) {
                throw new RuntimeException("La calificación debe estar entre 1 y 5");
            }
            testimonio.setCalificacion(calificacion);
        }

        return testimonioRep.save(testimonio);
    }

    public void eliminarTestimonio(Long id) {
        testimonioRep.deleteById(id);
    }
}