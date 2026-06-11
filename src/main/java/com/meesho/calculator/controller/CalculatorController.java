package com.meesho.calculator.controller;

import com.meesho.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Double>> add(@RequestParam double a, @RequestParam double b) {
        log.info("POST /add called with a={}, b={}", a, b);
        double result = calculatorService.add(a, b);
        return ResponseEntity.ok(Map.of("result", result));
    }

    @PostMapping("/subtract")
    public ResponseEntity<Map<String, Double>> subtract(@RequestParam double a, @RequestParam double b) {
        log.info("POST /subtract called with a={}, b={}", a, b);
        double result = calculatorService.subtract(a, b);
        return ResponseEntity.ok(Map.of("result", result));
    }

    @PostMapping("/multiply")
    public ResponseEntity<Map<String, Double>> multiply(@RequestParam double a, @RequestParam double b) {
        log.info("POST /multiply called with a={}, b={}", a, b);
        double result = calculatorService.multiply(a, b);
        return ResponseEntity.ok(Map.of("result", result));
    }

    @PostMapping("/divide")
    public ResponseEntity<?> divide(@RequestParam double a, @RequestParam double b) {
        log.info("POST /divide called with a={}, b={}", a, b);
        try {
            double result = calculatorService.divide(a, b);
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            log.error("Division error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/sqrt")
    public ResponseEntity<?> sqrt(@RequestParam double a) {
        log.info("POST /sqrt called with a={}", a);
        try {
            double result = calculatorService.sqrt(a);
            return ResponseEntity.ok(Map.of("result", result));
        } catch (IllegalArgumentException e) {
            log.error("sqrt error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}