package com.teamdev.calculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class CalculatorRecognizer implements AbstractRecognizer<States, CalculatorReader, CalculatorExpressionStack, BinaryOperatorException, CalculatorException> {

    CalculatorExpressionStack expressionStack;

    public CalculatorRecognizer(CalculatorExpressionStack expStack){
        expressionStack = expStack;
    }

    @Override
    public Boolean accept(States state, CalculatorReader expressionReader) throws BinaryOperatorException, CalculatorException{
        if (States.FINISH == state) {
            return true;
        } else if (States.RIGHT_BRACKET == state) {
            if (evalBracketClose(expressionReader)) {
                return true;
            }
        } else if (States.NUMBER == state) {
            if (addOperand(expressionReader)) {
                return true;
            }
        } else if (States.LEFT_BRACKET == state) {
            if (addBracketOpen(expressionReader)) {
                return true;
            }
        } else if (States.BINARY_OPERATOR == state) {
            if (addOperator(expressionReader)) {
                return true;
            }
        }
        return null;
    }

    private Boolean addOperand(CalculatorReader expressionReader) {
        BigDecimal operand = getOperand(expressionReader);
        if (null != operand) {
            //System.out.println("--addOperand = " + operand);
            expressionStack.operandStack.push(operand);
            return true;
        } else {
            return false;
        }
    }

    private Boolean evalBracketClose(CalculatorReader expressionReader) throws BinaryOperatorException, CalculatorException {
        String el = expressionReader.getSymbol();
        if (el.equals(")")) {
            //System.out.println("--evalBracketClose = " + el);
            if (0 == expressionStack.bracketStack.size()) {
                throw new CalculatorException("Open bracket is missing", expressionReader.getPosition());
            }
            expressionReader.incPosition();
            int lastOperatorSize = expressionStack.bracketStack.pop();
            int operatorSize = expressionStack.operatorStack.size();
            if (operatorSize == lastOperatorSize) {
                return true;
            } else {
                evaluateOperatorByCount(operatorSize - lastOperatorSize);
                return true;
            }
        }
        return false;
    }

    private Boolean addBracketOpen(CalculatorReader expressionReader) {
        String el = expressionReader.getSymbol();
        if (el.equals("(")) {
            //System.out.println("--addBracketOpen = " + el);
            expressionReader.incPosition();
            expressionStack.bracketStack.push(expressionStack.operatorStack.size());
            return true;
        }
        return false;
    }

    private Boolean addOperator(CalculatorReader expressionReader) throws BinaryOperatorException {
        String el = expressionReader.getSymbol();
        BinaryOperator lastBinaryOperator = expressionStack.binaryOperator.get(el);
        if (null != lastBinaryOperator) {
            expressionReader.incPosition();
            //System.out.println("--binaryOperator = " + el);
            int bracketStackCount;
            if (0 == expressionStack.bracketStack.size()) {
                bracketStackCount = 0;
            } else {
                bracketStackCount = expressionStack.bracketStack.peek();
            }
            while (!expressionStack.operatorStack.isEmpty() && ((0 == bracketStackCount) || (1 < (expressionStack.operatorStack.size() - bracketStackCount)))
                    && (expressionStack.operatorStack.peek().compareTo(lastBinaryOperator) < 1)) {
                //System.out.println("==operator = " + operatorStack.peek().getName());
                evaluateOperator();
            }
            expressionStack.operatorStack.push(lastBinaryOperator);
            return true;
        }
        return false;
    }

    private void evaluateOperator() throws BinaryOperatorException {
        BinaryOperator operator = expressionStack.operatorStack.pop();
        if (2 > expressionStack.operandStack.size()) {
            throw new BinaryOperatorException("binary operator wasn't calculated");
        }
        BigDecimal operandFirst = expressionStack.operandStack.pop();
        BigDecimal operandSecond = expressionStack.operandStack.pop();
        expressionStack.operandStack.push(operator.evaluate(operandFirst, operandSecond));
    }

    private void evaluateOperatorByCount(int n) throws BinaryOperatorException {
        int count = 0;
        while (count < n) {
            evaluateOperator();
            count++;
        }
    }
    private BigDecimal getOperand(CalculatorReader expressionReader) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        NumberFormat NUMBER_FORMAT = new DecimalFormat("0,0", decimalFormatSymbols);
        ParsePosition position = new ParsePosition(0);
        Number number = NUMBER_FORMAT.parse(expressionReader.getCurrentExpression(), position);

        if (number != null) {
            BigDecimal result = new BigDecimal(number.doubleValue());
            expressionReader.incPosition(position.getIndex());
            //System.out.println("--getOperand = " + result);
            return result;
        }
        return null;
    }
    public BigDecimal getResult() throws CalculatorException, BinaryOperatorException {
        while (!expressionStack.operatorStack.isEmpty()) {
            evaluateOperator();
        }
        if (!expressionStack.bracketStack.isEmpty()) {
            throw new BinaryOperatorException("missing closed bracket");
        }
        BigDecimal res = expressionStack.operandStack.pop();
        return res;
    }
}
