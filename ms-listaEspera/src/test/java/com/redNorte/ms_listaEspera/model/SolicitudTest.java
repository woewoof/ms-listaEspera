package com.redNorte.ms_listaEspera.model;

// Test de la entidad Solicitud
// Verifica que los métodos @PrePersist y @PreUpdate funcionan correctamente
// y que el Builder construye los objetos como se espera
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    // Test: verificar que @PrePersist setea fechas y valores por defecto
    @Test
    void onCreate_seteaFechasYEstadoPorDefecto() {
        Solicitud solicitud = new Solicitud();
        solicitud.onCreate();

        // Debe setear estado PENDIENTE por defecto
        assertEquals(Estado.PENDIENTE, solicitud.getEstado());
        // Debe setear prioridad NORMAL por defecto
        assertEquals(Prioridad.NORMAL, solicitud.getPrioridad());
        // Debe setear las fechas
        assertNotNull(solicitud.getCreadoEn());
        assertNotNull(solicitud.getActualizadoEn());
    }

    // Test: verificar que @PreUpdate actualiza la fecha
    @Test
    void onUpdate_actualizaFecha() {
        Solicitud solicitud = new Solicitud();
        solicitud.onUpdate();
        assertNotNull(solicitud.getActualizadoEn());
    }

    // Test: verificar que el Builder construye correctamente
    @Test
    void builder_construyeCorrectamente() {
        Solicitud solicitud = Solicitud.builder()
                .id(1L)
                .pacienteId(10L)
                .tipo(TipoSolicitud.CONSULTA)
                .estado(Estado.PENDIENTE)
                .prioridad(Prioridad.NORMAL)
                .build();

        assertEquals(1L, solicitud.getId());
        assertEquals(10L, solicitud.getPacienteId());
        assertEquals(TipoSolicitud.CONSULTA, solicitud.getTipo());
        assertEquals(Estado.PENDIENTE, solicitud.getEstado());
        assertEquals(Prioridad.NORMAL, solicitud.getPrioridad());
    }

    // Test: verificar que onCreate no sobreescribe estado si ya tiene uno
    @Test
    void onCreate_noSobreescribeEstadoExistente() {
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.ASIGNADO);
        solicitud.setPrioridad(Prioridad.URGENTE);
        solicitud.onCreate();

        // No debe sobreescribir porque ya tenía valores
        assertEquals(Estado.ASIGNADO, solicitud.getEstado());
        assertEquals(Prioridad.URGENTE, solicitud.getPrioridad());
    }
}
