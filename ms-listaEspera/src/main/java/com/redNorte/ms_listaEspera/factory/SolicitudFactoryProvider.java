package com.redNorte.ms_listaEspera.factory;

import com.redNorte.ms_listaEspera.model.TipoSolicitud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitudFactoryProvider {

    private final ConsultaFactory consultaFactory;
    private final CirugiaFactory cirugiaFactory;

    public SolicitudFactory obtener(TipoSolicitud tipo) {
        return switch (tipo) {
            case CONSULTA -> consultaFactory;
            case CIRUGIA -> cirugiaFactory;
        };
    }
}