package com.meesho.calculator.controller;

import com.meesho.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculatorControllerTest {

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private CalculatorController calculatorController;

    @Test
    void sqrt_positiveNumber_returnsResult() {
        when(calculatorService.sqrt(9.0)).thenReturn(3.0);
        ResponseEntity<?> response = calculatorController.sqrt(9.0);
        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(3.0, body.get("result"));
    }

    @Test
    void sqrt_negativeNumber_returnsBadRequest() {
        when(calculatorService.sqrt(-1.0)).thenThrow(new IllegalArgumentException("cannot sqrt negative number"));
        ResponseEntity<?> response = calculatorController.sqrt(-1.0);
        assertEquals(400, response.getStatusCodeValue());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("cannot sqrt negative number", body.get("error"));
    }
}