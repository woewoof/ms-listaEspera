package com.redNorte.ms_listaEspera.factory;

import com.redNorte.ms_listaEspera.dto.SolicitudRequest;
import com.redNorte.ms_listaEspera.model.Solicitud;
import com.redNorte.ms_listaEspera.model.TipoSolicitud;
import org.springframework.stereotype.Component;

@Component
public class ConsultaFactory implements SolicitudFactory {

    @Override
    public Solicitud crear(SolicitudRequest request) {
        return Solicitud.builder()
                .pacienteId(request.getPacienteId())
                .tipo(TipoSolicitud.CONSULTA)
                .prioridad(request.getPrioridad())
                .build();
    }
}