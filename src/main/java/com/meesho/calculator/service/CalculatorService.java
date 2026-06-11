package com.meesho.calculator.service;

import org.springframework.stereotype.Service;

/**
 * Core calculator logic. All four basic operations.
 * divide() throws IllegalArgumentException for b == 0.
 */
@Service
public class CalculatorService {

    public double calculate(double a, double b, String operation) {
        if (operation == null) {
            throw new IllegalArgumentException("operation must not be null");
        }
        switch (operation.trim().toLowerCase()) {
            case "add":      return a + b;
            case "subtract": return a - b;
            case "multiply": return a * b;
            case "divide":
                if (b == 0) throw new IllegalArgumentException("Cannot divide by zero");
                return a / b;
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation
                        + " — supported: add, subtract, multiply, divide");
        }
    }
}
