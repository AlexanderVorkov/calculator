package com.teamdev.consoleclient;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class CalculatorImpl {

    private HashMap<String, BinaryOperator> binaryOperator = new HashMap<String, BinaryOperator>();

    private Deque<BigDecimal> operandStack = new ArrayDeque<BigDecimal>();
    private Deque<BinaryOperator> operatorStack = new ArrayDeque<BinaryOperator>();
    private Deque<Integer> bracketStack = new ArrayDeque<Integer>();

    public CalculatorImpl() {
        binaryOperator.put("+", new PlusOperator(1));
        binaryOperator.put("-", new MinusOperator(1));
        binaryOperator.put("*", new MultiplOperator(2));
        binaryOperator.put("/", new DivideOperator(2));
    }

    public BigDecimal evaluate(String expression) {
        CalculatorReader expressionReader = new CalculatorReader(expression);
        String currentChar;
        while (!expressionReader.isEnd()) {
            String currentExpression = expressionReader.getCurrentExpression();
            //int position = expressionReader.getPosition();
            if (currentExpression.length() > 1) {
                currentChar = currentExpression.substring(0, 1);
            } else {
                currentChar = currentExpression;
            }
            //char element = expressionReader.getChar();
            expressionReader.incPosition();
            System.out.println("currentExpression= " + currentExpression);
            System.out.println("element= " + currentChar);
            if (!addOperand(currentChar) && !evalBracketClose(currentChar) && !addBracketOpen(currentChar)) {
                addOperator(currentChar);
            }
        }

        //BigDecimal result = new BigDecimal("2");

        //printOperand();
        //printOperator();
        return getResult();
    }

    private Boolean addOperand(String el) {
        try {
            BigDecimal operand = new BigDecimal(el);
            operandStack.push(operand);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Boolean evalBracketClose(String el) {
        if (el.equals(")")) {
            //TODO: Exception if empty list
            int lastOperatorSize = bracketStack.pop();
            int operatorSize = operatorStack.size();
            //TODO: Exception if operatorSize - lastOperatorSize < 0
            if(operatorSize == lastOperatorSize){
                return true;
            }else{
                evaluateOperatorByCount(operatorSize - lastOperatorSize);
                return true;
            }
        }
        return false;
    }

    private Boolean addBracketOpen(String el) {
        if (el.equals("(")) {
            bracketStack.push(operatorStack.size());
            return true;
        }
        return false;
    }

    private Boolean addOperator(String el) {
        System.out.println("--binaryOperator = " + el);
        int bracketStackCount;
        if(0==bracketStack.size()){
            bracketStackCount = 0;
        }else{
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
        return operandStack.pop();
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
    public static void main(String[] args) {
        //BigDecimal result = new CalculatorImpl().evaluate("2+3*4+2*2/8-2");
        //BigDecimal result = new CalculatorImpl().evaluate("4-8");
        //BigDecimal result = new CalculatorImpl().evaluate("2+2*2");
        //BigDecimal result = new CalculatorImpl().evaluate("222");
        //BigDecimal result = new CalculatorImpl().evaluate("(((2-1)-2)*9)+1");
        //BigDecimal result = new CalculatorImpl().evaluate("1+((9))");
        //BigDecimal result = new CalculatorImpl().evaluate("1+(1-3)*1");
        BigDecimal result = new CalculatorImpl().evaluate("(1)+(1+((8-2)-9)*9)");
        //BigDecimal result = new CalculatorImpl().evaluate("1-(1-3)*2");
        System.out.println("====result = " + result);
    }
}