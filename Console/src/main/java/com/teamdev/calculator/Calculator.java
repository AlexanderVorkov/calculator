package com.teamdev.calculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class Calculator {

    private HashMap<String, BinaryOperator> binaryOperator = new HashMap<String, BinaryOperator>();

    private Deque<BigDecimal> operandStack = new ArrayDeque<BigDecimal>();
    private Deque<BinaryOperator> operatorStack = new ArrayDeque<BinaryOperator>();
    private Deque<Integer> bracketStack = new ArrayDeque<Integer>();

    public Calculator() {
        binaryOperator.put("+", new PlusOperator(1));
        binaryOperator.put("-", new MinusOperator(1));
        binaryOperator.put("*", new MultiplOperator(2));
        binaryOperator.put("/", new DivideOperator(2));
    }

    private States evaluateState(States[] listState, CalculatorReader expressionReader) {
        States state;
        Boolean status = false;
        for (int i = 0; i < listState.length; i++) {
            state = listState[i];
            if (States.FINISH == state) {
                return States.FINISH;
            } else if (States.RIGHT_BRACKET == state) {
                if (evalBracketClose(expressionReader)) {
                    return States.RIGHT_BRACKET;
                }
            } else if (States.NUMBER == state) {
                if (addOperand(expressionReader)) {
                    return States.NUMBER;
                }
            } else if (States.LEFT_BRACKET == state) {
                if (addBracketOpen(expressionReader)) {
                    return States.LEFT_BRACKET;
                }
            } else if (States.BINARY_OPERATOR == state) {
                if (addOperator(expressionReader)) {
                    return States.BINARY_OPERATOR;
                }
            }
        }
        return null;
    }

    public BigDecimal evaluate(String expression) {
        CalculatorReader expressionReader = new CalculatorReader(expression);
        TransitionMatrix trMatrix = new TransitionMatrix();
        States state = States.START;
        while (!expressionReader.isEnd()) {
            States[] listState = trMatrix.getTransition(state);
            state = evaluateState(listState, expressionReader);
        }

        //BigDecimal result = new BigDecimal("2");

        //printOperand();
        //printOperator();
        return getResult();
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
            System.out.println("--getOperand = " + result);
            return result;
        }
        return null;
    }

    private Boolean addOperand(CalculatorReader expressionReader) {
        BigDecimal operand = getOperand(expressionReader);
        if (null != operand) {
            System.out.println("--addOperand = " + operand);
            operandStack.push(operand);
            return true;
        } else {
            return false;
        }
    }

    private String getSymbol(CalculatorReader expressionReader) {
        String currentExpression = expressionReader.getCurrentExpression();
        String currentChar;
        if (currentExpression.length() > 1) {
            currentChar = currentExpression.substring(0, 1);
        } else {
            currentChar = currentExpression;
        }
        return currentChar;
    }

    private Boolean evalBracketClose(CalculatorReader expressionReader) {
        String el = getSymbol(expressionReader);
        if (el.equals(")")) {
            System.out.println("--evalBracketClose = " + el);
            expressionReader.incPosition();
            //TODO: Exception if empty list
            int lastOperatorSize = bracketStack.pop();
            int operatorSize = operatorStack.size();
            //TODO: Exception if operatorSize - lastOperatorSize < 0
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
        String el = getSymbol(expressionReader);
        if (el.equals("(")) {
            System.out.println("--addBracketOpen = " + el);
            expressionReader.incPosition();
            bracketStack.push(operatorStack.size());
            return true;
        }
        return false;
    }

    private Boolean addOperator(CalculatorReader expressionReader) {
        String el = getSymbol(expressionReader);
        if (el.equals("+") || el.equals("-") || el.equals("*") || el.equals("/")) {
            //TODO: if el != Operator
            expressionReader.incPosition();
            System.out.println("--binaryOperator = " + el);
            int bracketStackCount;
            if (0 == bracketStack.size()) {
                bracketStackCount = 0;
            } else {
                bracketStackCount = bracketStack.peek();
            }
            BinaryOperator lastBinaryOperator = (BinaryOperator) binaryOperator.get(el);
            while (!operatorStack.isEmpty() && ((0 == bracketStackCount) || (1 < (operatorStack.size() - bracketStackCount)))
                    && (operatorStack.peek().compareTo(lastBinaryOperator) < 1)) {
                System.out.println("==operator = " + operatorStack.peek().getName());
                evaluateOperator();
            }
            //TODO: Exception
            operatorStack.push(binaryOperator.get(el));
            return true;
        }
        return false;
    }

    private void evaluateOperator() {
        //TODO: exception if operatorStack == empty or (count of operandStack < 2)
        BinaryOperator operator = operatorStack.pop();
        BigDecimal operandFirst = operandStack.pop();
        BigDecimal operandSecond = operandStack.pop();
        operandStack.push(operator.evaluate(operandFirst, operandSecond));
    }

    private void evaluateOperatorByCount(int n) {
        int count = 0;
        while (count < n) {
            evaluateOperator();
            count++;
        }
    }

    private BigDecimal getResult() {
        //TODO: exception if bracketStack not Empty
        while (!operatorStack.isEmpty()) {
            evaluateOperator();
        }
        //TODO:
        //if operandStack = 1 then Result
        //else exception
        BigDecimal res = operandStack.pop();
        //BigDecimal resRound = res.setScale(2, BigDecimal.ROUND_HALF_UP);
        //BigDecimal.valueOf(12)

        return res;
    }

    //test
    private void printOperand() {
        for (Object de : operandStack) {
            System.out.println("printOperand");
            System.out.println(de + "; ");
        }
    }

    //test
    private void printOperator() {
        BinaryOperator operator;
        while (!operatorStack.isEmpty()) {
            operator = operatorStack.pop();
            System.out.println("printOperator");
            System.out.println(operator.getName() + "; ");
        }
    }
}