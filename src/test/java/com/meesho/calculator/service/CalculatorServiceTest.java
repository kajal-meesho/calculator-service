package com.meesho.calculator.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private final CalculatorService service = new CalculatorService();

    // ---- add ----

    @Test
    void add_positivePlusPositive() {
        assertEquals(5.0, service.calculate(2, 3, "add"), 0.001);
    }

    @Test
    void add_negativePlusPositive() {
        assertEquals(-1.0, service.calculate(-3, 2, "add"), 0.001);
    }

    @Test
    void add_zeros() {
        assertEquals(0.0, service.calculate(0, 0, "add"), 0.001);
    }

    // ---- subtract ----

    @Test
    void subtract_positives() {
        assertEquals(1.0, service.calculate(3, 2, "subtract"), 0.001);
    }

    @Test
    void subtract_sameNegatives() {
        assertEquals(0.0, service.calculate(-2, -2, "subtract"), 0.001);
    }

    // ---- multiply ----

    @Test
    void multiply_positives() {
        assertEquals(6.0, service.calculate(2, 3, "multiply"), 0.001);
    }

    @Test
    void multiply_byZero() {
        assertEquals(0.0, service.calculate(5, 0, "multiply"), 0.001);
    }

    // ---- divide ----

    @Test
    void divide_positiveDivisor() {
        assertEquals(2.5, service.calculate(5, 2, "divide"), 0.001);
    }

    @Test
    void divide_byZero_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculate(1, 0, "divide"));
    }

    // ---- operation validation ----

    @Test
    void unknownOperation_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculate(1, 2, "modulo"));
    }

    @Test
    void nullOperation_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculate(1, 2, null));
    }

    @Test
    void operationIsCaseInsensitive() {
        assertEquals(5.0, service.calculate(2, 3, "ADD"), 0.001);
        assertEquals(5.0, service.calculate(2, 3, "Add"), 0.001);
    }

    // ---- sqrt ----

    @Test
    void sqrt_positive_returnsSquareRoot() {
        assertEquals(3.0, service.sqrt(9), 0.001);
    }

    @Test
    void sqrt_negative_throwsIllegalArgument() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.sqrt(-1));
        assertEquals("cannot sqrt negative number", ex.getMessage());
    }

    @Test
    void sqrt_zero_returnsZero() {
        assertEquals(0.0, service.sqrt(0), 0.001);
    }
}