package com.redNorte.ms_listaEspera.factory;

import com.redNorte.ms_listaEspera.dto.SolicitudRequest;
import com.redNorte.ms_listaEspera.model.Prioridad;
import com.redNorte.ms_listaEspera.model.Solicitud;
import com.redNorte.ms_listaEspera.model.TipoSolicitud;
import org.springframework.stereotype.Component;

@Component
public class CirugiaFactory implements SolicitudFactory {

    @Override
    public Solicitud crear(SolicitudRequest request) {
        return Solicitud.builder()
                .pacienteId(request.getPacienteId())
                .tipo(TipoSolicitud.CIRUGIA)
                .prioridad(request.getPrioridad() != null ? request.getPrioridad() : Prioridad.URGENTE)
                .build();
    }
}