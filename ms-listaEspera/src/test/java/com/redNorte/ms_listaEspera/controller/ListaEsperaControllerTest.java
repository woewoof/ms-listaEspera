package com.redNorte.ms_listaEspera.controller;

// Test del controlador REST usando MockMvc
// Simula peticiones HTTP sin levantar el servidor completo
// Verifica que los endpoints responden correctamente
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redNorte.ms_listaEspera.dto.SolicitudRequest;
import com.redNorte.ms_listaEspera.dto.SolicitudResponse;
import com.redNorte.ms_listaEspera.model.Estado;
import com.redNorte.ms_listaEspera.model.Prioridad;
import com.redNorte.ms_listaEspera.model.TipoSolicitud;
import com.redNorte.ms_listaEspera.service.ListaEsperaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ListaEsperaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ListaEsperaService service;

    @InjectMocks
    private ListaEsperaController controller;

    private ObjectMapper objectMapper = new ObjectMapper();
    private SolicitudResponse responseMock;

    @BeforeEach
    void setUp() {
        // Configuramos MockMvc con el controller
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Creamos una respuesta mock para reutilizar en los tests
        responseMock = SolicitudResponse.builder()
                .id(1L)
                .pacienteId(10L)
                .tipo(TipoSolicitud.CONSULTA)
                .estado(Estado.PENDIENTE)
                .prioridad(Prioridad.NORMAL)
                .creadoEn(LocalDateTime.now())
                .actualizadoEn(LocalDateTime.now())
                .build();
    }

    // Test: POST /api/solicitudes → debe retornar 201 CREATED
    @Test
    void crear_retorna201() throws Exception {
        SolicitudRequest request = new SolicitudRequest();
        request.setPacienteId(10L);
        request.setTipo(TipoSolicitud.CONSULTA);
        request.setPrioridad(Prioridad.NORMAL);

        when(service.crear(any())).thenReturn(responseMock);

        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pacienteId").value(10));
    }

    // Test: GET /api/solicitudes → debe retornar lista con 200 OK
    @Test
    void obtenerTodas_retorna200() throws Exception {
        when(service.obtenerTodas()).thenReturn(List.of(responseMock));

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // Test: GET /api/solicitudes/{id} → debe retornar la solicitud con 200 OK
    @Test
    void obtenerPorId_retorna200() throws Exception {
        when(service.obtenerPorId(1L)).thenReturn(responseMock);

        mockMvc.perform(get("/api/solicitudes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // Test: GET /api/solicitudes/paciente/{id} → debe retornar lista del paciente
    @Test
    void obtenerPorPaciente_retorna200() throws Exception {
        when(service.obtenerPorPaciente(10L)).thenReturn(List.of(responseMock));

        mockMvc.perform(get("/api/solicitudes/paciente/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pacienteId").value(10));
    }

    // Test: PATCH /api/solicitudes/{id}/estado → debe cambiar estado
    @Test
    void cambiarEstado_retorna200() throws Exception {
        SolicitudResponse asignada = SolicitudResponse.builder()
                .id(1L).pacienteId(10L)
                .tipo(TipoSolicitud.CONSULTA)
                .estado(Estado.ASIGNADO)
                .prioridad(Prioridad.NORMAL)
                .creadoEn(LocalDateTime.now())
                .actualizadoEn(LocalDateTime.now())
                .build();

        when(service.cambiarEstado(eq(1L), eq(Estado.ASIGNADO))).thenReturn(asignada);

        mockMvc.perform(patch("/api/solicitudes/1/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("estado", "ASIGNADO"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ASIGNADO"));
    }

    // Test: DELETE /api/solicitudes/{id} → debe cancelar la solicitud
    @Test
    void cancelar_retorna200() throws Exception {
        SolicitudResponse cancelada = SolicitudResponse.builder()
                .id(1L).pacienteId(10L)
                .tipo(TipoSolicitud.CONSULTA)
                .estado(Estado.CANCELADO)
                .prioridad(Prioridad.NORMAL)
                .creadoEn(LocalDateTime.now())
                .actualizadoEn(LocalDateTime.now())
                .build();

        when(service.cancelar(1L)).thenReturn(cancelada);

        mockMvc.perform(delete("/api/solicitudes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CANCELADO"));
    }
}
