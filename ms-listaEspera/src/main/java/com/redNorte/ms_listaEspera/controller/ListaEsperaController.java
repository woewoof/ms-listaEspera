package com.redNorte.ms_listaEspera.controller;

import com.redNorte.ms_listaEspera.dto.SolicitudRequest;
import com.redNorte.ms_listaEspera.dto.SolicitudResponse;
import com.redNorte.ms_listaEspera.model.Estado;
import com.redNorte.ms_listaEspera.service.ListaEsperaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ListaEsperaController {

    private final ListaEsperaService service;


    @PostMapping
    public ResponseEntity<SolicitudResponse> crear(@Valid @RequestBody SolicitudRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }


    @GetMapping
    public ResponseEntity<List<SolicitudResponse>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }


    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<SolicitudResponse>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }


    @PatchMapping("/{id}/estado")
    public ResponseEntity<SolicitudResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        Estado nuevoEstado = Estado.valueOf(body.get("estado").toUpperCase());
        return ResponseEntity.ok(service.cambiarEstado(id, nuevoEstado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<SolicitudResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
