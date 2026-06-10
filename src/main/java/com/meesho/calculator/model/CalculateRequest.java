package com.meesho.calculator.model;

import lombok.Data;

/**
 * Request body for POST /calculate.
 * operation: one of "add", "subtract", "multiply", "divide"
 */
@Data
public class CalculateRequest {
    private double a;
    private double b;
    private String operation;
}
