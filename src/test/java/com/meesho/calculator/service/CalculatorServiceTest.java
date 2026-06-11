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

    // ---- compound ----

    @Test
    void compound_normalCase_returnsCorrectValue() {
        double result = service.compound(2.0, 4.0, 6.0);
        // 2 * (1 + 6/4) / 4 = 2 * 2.5 / 4 = 1.25
        assertEquals(1.25, result, 0.001);
    }

    @Test
    void compound_bIsZero_throwsIllegalArgument() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.compound(1.0, 0.0, 2.0));
        assertEquals("b cannot be zero", ex.getMessage());
    }

    // ---- power ----

    @Test
    void power_normalCase_returnsCorrectValue() {
        // 2^3 = 8
        assertEquals(8.0, service.power(2.0, 3.0), 0.001);
        // 5^0 = 1
        assertEquals(1.0, service.power(5.0, 0.0), 0.001);
        // 0^5 = 0
        assertEquals(0.0, service.power(0.0, 5.0), 0.001);
        // 2^(-2) = 0.25
        assertEquals(0.25, service.power(2.0, -2.0), 0.001);
    }

    @Test
    void power_zeroToZero_throwsIllegalArgument() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.power(0.0, 0.0));
        assertEquals("0^0 is undefined", ex.getMessage());
    }

    // ---- sqrt ----

    @Test
    void sqrt_positiveNumber_returnsSquareRoot() {
        assertEquals(3.0, service.sqrt(9.0), 0.001);
        assertEquals(0.0, service.sqrt(0.0), 0.001);
        assertEquals(Math.sqrt(2.0), service.sqrt(2.0), 0.001);
    }

    @Test
    void sqrt_negativeNumber_throwsIllegalArgument() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.sqrt(-1.0));
        assertEquals("cannot sqrt negative number", ex.getMessage());
    }
}