package com.teamdev.calculator;

import java.math.BigDecimal;

public class Calculator extends AbstractStateMachine<BigDecimal,
        BinaryOperatorException,
        CalculatorException,
        CalculatorReader,
        States,
        TransitionMatrix,
        CalculatorExpressionStack,
        CalculatorRecognizer> {

    private CalculatorExpressionStack expressionStack = new CalculatorExpressionStack();
    private CalculatorReader reader;
    private TransitionMatrix transitionMatrix = new TransitionMatrix();
    private CalculatorRecognizer calculatorRecognizer = new CalculatorRecognizer(expressionStack);

    public BigDecimal evaluate(String expression) throws BinaryOperatorException, CalculatorException {
        expression = expression.replaceAll("\\s+", "");
        reader = new CalculatorReader(expression);
        return run(reader);
    }

    @Override
    protected void stateException(CalculatorReader expressionReader) throws CalculatorException {
        throw new CalculatorException("unknown operator or invalid transition", expressionReader.getPosition());
    }

    @Override
    protected CalculatorExpressionStack getExpressionStack() {
        return expressionStack;
    }

    protected CalculatorReader getReader() {
        return reader;
    }

    @Override
    protected TransitionMatrix getTransitionMatrix() {
        return transitionMatrix;
    }

    @Override
    protected CalculatorRecognizer getRecognizer() {
        return calculatorRecognizer;
    }

    @Override
    public BigDecimal getResult() throws CalculatorException, BinaryOperatorException {
        return getRecognizer().getResult();
    }
}