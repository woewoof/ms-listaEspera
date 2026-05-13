package com.redNorte.ms_listaEspera.repository;

import com.redNorte.ms_listaEspera.model.Estado;
import com.redNorte.ms_listaEspera.model.Prioridad;
import com.redNorte.ms_listaEspera.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(Estado estado);
    List<Solicitud> findByPacienteId(Long pacienteId);
    List<Solicitud> findByPrioridad(Prioridad prioridad);
    List<Solicitud> findByEstadoOrderByPrioridadAscCreadoEnAsc(Estado estado);
}
