package com.redNorte.ms_listaEspera.exception;

// Test del manejador global de excepciones
// Verifica que cada tipo de error retorna el código HTTP correcto
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    // Test: SolicitudNotFoundException → debe retornar 404
    @Test
    void handleNotFound_retorna404() {
        SolicitudNotFoundException ex = new SolicitudNotFoundException("Solicitud no encontrada con id: 99");

        ResponseEntity<Map<String, Object>> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Solicitud no encontrada con id: 99", response.getBody().get("error"));
    }

    // Test: IllegalArgumentException → debe retornar 400
    @Test
    void handleIllegalArgument_retorna400() {
        IllegalArgumentException ex = new IllegalArgumentException("INVALIDO");

        ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertTrue(response.getBody().get("error").toString().contains("INVALIDO"));
    }

    // Test: MethodArgumentNotValidException → debe retornar 400 con errores de campo
    @Test
    void handleValidation_retorna400() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("solicitud", "pacienteId", "El pacienteId es obligatorio");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<Map<String, Object>> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertNotNull(response.getBody().get("errores"));
    }

    // Test: SolicitudNotFoundException con mensaje vacío
    @Test
    void handleNotFound_mensajeVacio() {
        SolicitudNotFoundException ex = new SolicitudNotFoundException("");
        ResponseEntity<Map<String, Object>> response = handler.handleNotFound(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
