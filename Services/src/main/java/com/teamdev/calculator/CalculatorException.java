package com.teamdev.calculator;

public class CalculatorException extends Exception {

    private int errorPosition;

    public CalculatorException(String message, int errorPosition) {
        super(message);
        this.errorPosition = errorPosition;
    }

    public int getErrorPosition() {
        return errorPosition;
    }
}
