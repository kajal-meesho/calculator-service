package com.meesho.calculator;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    @PostMapping(value = "/plus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CalculatorResponse plus(@RequestBody CalculatorRequest req) {
        return new CalculatorResponse(req.getA() + req.getB(), "plus",
                req.getA() + " + " + req.getB());
    }

    @PostMapping(value = "/minus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CalculatorResponse minus(@RequestBody CalculatorRequest req) {
        return new CalculatorResponse(req.getA() - req.getB(), "minus",
                req.getA() + " - " + req.getB());
    }

    @PostMapping(value = "/multiply", produces = MediaType.APPLICATION_JSON_VALUE)
    public CalculatorResponse multiply(@RequestBody CalculatorRequest req) {
        return new CalculatorResponse(req.getA() * req.getB(), "multiply",
                req.getA() + " × " + req.getB());
    }

    @PostMapping(value = "/divide", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> divide(@RequestBody CalculatorRequest req) {
        if (req.getB() == 0) {
            return ResponseEntity.badRequest().body("{\"error\": \"Division by zero is not allowed\"}");
        }
        return ResponseEntity.ok(new CalculatorResponse(req.getA() / req.getB(), "divide",
                req.getA() + " ÷ " + req.getB()));
    }
}
