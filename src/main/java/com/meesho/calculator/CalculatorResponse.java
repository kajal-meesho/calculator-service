package com.meesho.calculator;

public class CalculatorResponse {
    private double result;
    private String operation;
    private String expression;

    public CalculatorResponse(double result, String operation, String expression) {
        this.result = result;
        this.operation = operation;
        this.expression = expression;
    }

    public double getResult() { return result; }
    public String getOperation() { return operation; }
    public String getExpression() { return expression; }
}
