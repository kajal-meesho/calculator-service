package com.meesho.calculator.service;

import org.springframework.stereotype.Service;

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

    public double power(double a, double b) {
        if (a == 0 && b == 0) {
            throw new IllegalArgumentException("0^0 is undefined");
        }
        return Math.pow(a, b);
    }
}