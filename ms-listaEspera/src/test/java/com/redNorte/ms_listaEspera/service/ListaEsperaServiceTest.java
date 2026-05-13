package com.redNorte.ms_listaEspera.service;

import com.redNorte.ms_listaEspera.dto.SolicitudRequest;
import com.redNorte.ms_listaEspera.dto.SolicitudResponse;
import com.redNorte.ms_listaEspera.exception.SolicitudNotFoundException;
import com.redNorte.ms_listaEspera.factory.CirugiaFactory;
import com.redNorte.ms_listaEspera.factory.ConsultaFactory;
import com.redNorte.ms_listaEspera.factory.SolicitudFactoryProvider;
import com.redNorte.ms_listaEspera.model.*;
import com.redNorte.ms_listaEspera.repository.SolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListaEsperaServiceTest {

    @Mock private SolicitudRepository repository;
    @Mock private SolicitudFactoryProvider factoryProvider;
    @Mock private ConsultaFactory consultaFactory;
    @Mock private CirugiaFactory cirugiaFactory;

    @InjectMocks
    private ListaEsperaService service;

    private Solicitud solicitudMock;

    @BeforeEach
    void setUp() {
        solicitudMock = Solicitud.builder()
                .id(1L).pacienteId(10L)
                .tipo(TipoSolicitud.CONSULTA)
                .estado(Estado.PENDIENTE)
                .prioridad(Prioridad.NORMAL)
                .creadoEn(LocalDateTime.now())
                .actualizadoEn(LocalDateTime.now())
                .build();
    }

    @Test
    void crear_consulta_exitoso() {
        SolicitudRequest request = new SolicitudRequest();
        request.setPacienteId(10L);
        request.setTipo(TipoSolicitud.CONSULTA);
        request.setPrioridad(Prioridad.NORMAL);

        when(factoryProvider.obtener(TipoSolicitud.CONSULTA)).thenReturn(consultaFactory);
        when(consultaFactory.crear(request)).thenReturn(solicitudMock);
        when(repository.save(any())).thenReturn(solicitudMock);

        SolicitudResponse response = service.crear(request);
        assertNotNull(response);
        assertEquals(10L, response.getPacienteId());
    }

    @Test
    void crear_cirugia_exitoso() {
        Solicitud cirugiaMock = Solicitud.builder()
                .id(2L).pacienteId(20L)
                .tipo(TipoSolicitud.CIRUGIA)
                .estado(Estado.PENDIENTE)
                .prioridad(Prioridad.URGENTE)
                .creadoEn(LocalDateTime.now())
                .actualizadoEn(LocalDateTime.now())
                .build();

        SolicitudRequest request = new SolicitudRequest();
        request.setPacienteId(20L);
        request.setTipo(TipoSolicitud.CIRUGIA);
        request.setPrioridad(Prioridad.URGENTE);

        when(factoryProvider.obtener(TipoSolicitud.CIRUGIA)).thenReturn(cirugiaFactory);
        when(cirugiaFactory.crear(request)).thenReturn(cirugiaMock);
        when(repository.save(any())).thenReturn(cirugiaMock);

        SolicitudResponse response = service.crear(request);
        assertEquals(Prioridad.URGENTE, response.getPrioridad());
    }

    @Test
    void obtenerTodas_retornaLista() {
        when(repository.findAll()).thenReturn(List.of(solicitudMock));
        assertEquals(1, service.obtenerTodas().size());
    }

    @Test
    void obtenerPorId_exitoso() {
        when(repository.findById(1L)).thenReturn(Optional.of(solicitudMock));
        assertEquals(1L, service.obtenerPorId(1L).getId());
    }

    @Test
    void obtenerPorId_noEncontrado_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(SolicitudNotFoundException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void cambiarEstado_exitoso() {
        Solicitud actualizada = Solicitud.builder()
                .id(1L).pacienteId(10L)
                .tipo(TipoSolicitud.CONSULTA)
                .estado(Estado.ASIGNADO)
                .prioridad(Prioridad.NORMAL)
                .creadoEn(LocalDateTime.now())
                .actualizadoEn(LocalDateTime.now())
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(solicitudMock));
        when(repository.save(any())).thenReturn(actualizada);
        assertEquals(Estado.ASIGNADO, service.cambiarEstado(1L, Estado.ASIGNADO).getEstado());
    }

    @Test
    void cancelar_exitoso() {
        Solicitud cancelada = Solicitud.builder()
                .id(1L).pacienteId(10L)
                .tipo(TipoSolicitud.CONSULTA)
                .estado(Estado.CANCELADO)
                .prioridad(Prioridad.NORMAL)
                .creadoEn(LocalDateTime.now())
                .actualizadoEn(LocalDateTime.now())
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(solicitudMock));
        when(repository.save(any())).thenReturn(cancelada);
        assertEquals(Estado.CANCELADO, service.cancelar(1L).getEstado());
    }

    @Test
    void obtenerPorPaciente_retornaLista() {
        when(repository.findByPacienteId(10L)).thenReturn(List.of(solicitudMock));
        assertEquals(1, service.obtenerPorPaciente(10L).size());
    }
}
