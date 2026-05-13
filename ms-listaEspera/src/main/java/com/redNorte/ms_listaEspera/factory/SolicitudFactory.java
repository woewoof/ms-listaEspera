package com.redNorte.ms_listaEspera.factory;

import com.redNorte.ms_listaEspera.dto.SolicitudRequest;
import com.redNorte.ms_listaEspera.model.Solicitud;

public interface SolicitudFactory {
    Solicitud crear(SolicitudRequest request);
}