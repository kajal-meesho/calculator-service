package com.meesho.calculator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void add_positiveNumbers_returnsSum() {
        assertEquals(5.0, calculatorService.add(2.0, 3.0));
    }

    @Test
    void subtract_positiveNumbers_returnsDifference() {
        assertEquals(2.0, calculatorService.subtract(5.0, 3.0));
    }

    @Test
    void multiply_positiveNumbers_returnsProduct() {
        assertEquals(15.0, calculatorService.multiply(3.0, 5.0));
    }

    @Test
    void divide_positiveNumbers_returnsQuotient() {
        assertEquals(2.0, calculatorService.divide(6.0, 3.0));
    }

    @Test
    void divide_byZero_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> calculatorService.divide(1.0, 0.0));
    }

    // New sqrt tests
    @Test
    void sqrt_positiveNumber_returnsSquareRoot() {
        assertEquals(3.0, calculatorService.sqrt(9.0), 1e-9);
    }

    @Test
    void sqrt_zero_returnsZero() {
        assertEquals(0.0, calculatorService.sqrt(0.0));
    }

    @Test
    void sqrt_negativeNumber_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> calculatorService.sqrt(-1.0));
    }
}