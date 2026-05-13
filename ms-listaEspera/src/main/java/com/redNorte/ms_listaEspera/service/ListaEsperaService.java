package com.redNorte.ms_listaEspera.service;

import com.redNorte.ms_listaEspera.dto.SolicitudRequest;
import com.redNorte.ms_listaEspera.dto.SolicitudResponse;
import com.redNorte.ms_listaEspera.exception.SolicitudNotFoundException;
import com.redNorte.ms_listaEspera.factory.SolicitudFactoryProvider;
import com.redNorte.ms_listaEspera.model.Estado;
import com.redNorte.ms_listaEspera.model.Solicitud;
import com.redNorte.ms_listaEspera.repository.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListaEsperaService {

    private final SolicitudRepository repository;
    private final SolicitudFactoryProvider factoryProvider;

    public SolicitudResponse crear(SolicitudRequest request) {
        Solicitud solicitud = factoryProvider.obtener(request.getTipo()).crear(request);
        return toResponse(repository.save(solicitud));
    }

    public List<SolicitudResponse> obtenerTodas() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SolicitudResponse obtenerPorId(Long id) {
        Solicitud solicitud = repository.findById(id)
                .orElseThrow(() -> new SolicitudNotFoundException("Solicitud no encontrada con id: " + id));
        return toResponse(solicitud);
    }

    public List<SolicitudResponse> obtenerPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SolicitudResponse cambiarEstado(Long id, Estado nuevoEstado) {
        Solicitud solicitud = repository.findById(id)
                .orElseThrow(() -> new SolicitudNotFoundException("Solicitud no encontrada con id: " + id));
        solicitud.setEstado(nuevoEstado);
        return toResponse(repository.save(solicitud));
    }

    public SolicitudResponse cancelar(Long id) {
        return cambiarEstado(id, Estado.CANCELADO);
    }

    private SolicitudResponse toResponse(Solicitud s) {
        return SolicitudResponse.builder()
                .id(s.getId())
                .pacienteId(s.getPacienteId())
                .tipo(s.getTipo())
                .estado(s.getEstado())
                .prioridad(s.getPrioridad())
                .creadoEn(s.getCreadoEn())
                .actualizadoEn(s.getActualizadoEn())
                .build();
    }
}