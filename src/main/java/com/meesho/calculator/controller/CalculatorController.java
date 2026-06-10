package com.meesho.calculator.controller;

import com.meesho.calculator.model.CalculateRequest;
import com.meesho.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST API for the calculator service.
 *
 * POST /calculate  — { a, b, operation } → { result }
 * GET  /health     — { status: "UP" }
 */
@RestController
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculate(@RequestBody CalculateRequest req) {
        try {
            double result = calculatorService.calculate(req.getA(), req.getB(), req.getOperation());
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
