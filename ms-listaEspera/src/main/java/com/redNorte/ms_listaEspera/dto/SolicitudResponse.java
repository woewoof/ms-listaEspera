package com.redNorte.ms_listaEspera.dto;
import com.redNorte.ms_listaEspera.model.Estado;
import  com.redNorte.ms_listaEspera.model.Prioridad;
import com.redNorte.ms_listaEspera.model.TipoSolicitud;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
    @Builder

    public class SolicitudResponse {
        private Long id;
        private Long pacienteId;
        private TipoSolicitud tipo;
        private Estado estado;
        private Prioridad prioridad;
        private LocalDateTime creadoEn;
        private LocalDateTime actualizadoEn;
}
