package com.redNorte.ms_listaEspera.factory;

import com.redNorte.ms_listaEspera.dto.SolicitudRequest;
import com.redNorte.ms_listaEspera.model.Prioridad;
import com.redNorte.ms_listaEspera.model.Solicitud;
import com.redNorte.ms_listaEspera.model.TipoSolicitud;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {

    @Test
    void consultaFactory_crea_tipo_consulta() {
        ConsultaFactory factory = new ConsultaFactory();
        SolicitudRequest request = new SolicitudRequest();
        request.setPacienteId(1L);
        request.setTipo(TipoSolicitud.CONSULTA);
        request.setPrioridad(Prioridad.NORMAL);

        Solicitud result = factory.crear(request);
        assertEquals(TipoSolicitud.CONSULTA, result.getTipo());
        assertEquals(Prioridad.NORMAL, result.getPrioridad());
    }

    @Test
    void cirugiaFactory_crea_tipo_cirugia_urgente() {
        CirugiaFactory factory = new CirugiaFactory();
        SolicitudRequest request = new SolicitudRequest();
        request.setPacienteId(2L);
        request.setTipo(TipoSolicitud.CIRUGIA);
        request.setPrioridad(null);

        Solicitud result = factory.crear(request);
        assertEquals(TipoSolicitud.CIRUGIA, result.getTipo());
        assertEquals(Prioridad.URGENTE, result.getPrioridad());
    }

    @Test
    void factoryProvider_retorna_consultaFactory() {
        SolicitudFactoryProvider provider = new SolicitudFactoryProvider(new ConsultaFactory(), new CirugiaFactory());
        assertInstanceOf(ConsultaFactory.class, provider.obtener(TipoSolicitud.CONSULTA));
    }

    @Test
    void factoryProvider_retorna_cirugiaFactory() {
        SolicitudFactoryProvider provider = new SolicitudFactoryProvider(new ConsultaFactory(), new CirugiaFactory());
        assertInstanceOf(CirugiaFactory.class, provider.obtener(TipoSolicitud.CIRUGIA));
    }
}
