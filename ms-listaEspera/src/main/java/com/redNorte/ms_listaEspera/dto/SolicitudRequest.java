package com.redNorte.ms_listaEspera.dto;

import com.redNorte.ms_listaEspera.model.Prioridad;
import com.redNorte.ms_listaEspera.model.TipoSolicitud;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitudRequest {
    @NotNull(message = "El paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "el tipo es obligatorio (CONSULTA o CIRUGIA)")
    private TipoSolicitud tipo;
    private  Prioridad prioridad = Prioridad.NORMAL;
}
