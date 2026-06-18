package com.meesho.calculator;

public class CalculatorResponse {
    private double result;
    private String operation;
    private String expression;
    private Double input; // Use Double to allow null for non-square operations

    public CalculatorResponse(double result, String operation, String expression) {
        this.result = result;
        this.operation = operation;
        this.expression = expression;
        this.input = null;
    }

    public CalculatorResponse(double result, String operation, String expression, double input) {
        this.result = result;
        this.operation = operation;
        this.expression = expression;
        this.input = input;
    }

    public double getResult() { return result; }
    public String getOperation() { return operation; }
    public String getExpression() { return expression; }
    public Double getInput() { return input; }
}