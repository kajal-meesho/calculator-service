package com.meesho.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CalculatorService {

    public double add(double a, double b) {
        log.info("Adding {} + {}", a, b);
        return a + b;
    }

    public double subtract(double a, double b) {
        log.info("Subtracting {} - {}", a, b);
        return a - b;
    }

    public double multiply(double a, double b) {
        log.info("Multiplying {} * {}", a, b);
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        log.info("Dividing {} / {}", a, b);
        return a / b;
    }

    public double sqrt(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("cannot sqrt negative number");
        }
        log.info("Square root of {}", a);
        return Math.sqrt(a);
    }
}