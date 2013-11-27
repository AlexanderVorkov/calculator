package com.teamdev.calculator;

public class CalculatorReader implements AbstractReader {
    private String expression;
    private int position = 0;

    public CalculatorReader(String expression){
        this.expression = expression;
    }

    public String getSymbol() {
        String currentChar;
        String expr = getCurrentExpression();
        if (expr.length() > 1) {
            currentChar = expr.substring(0, 1);
        } else {
            currentChar = expr;
        }
        return currentChar;
    }

    public int getPosition(){
        return position;
    }

    public void incPosition(){
        position++;
    }

    public void incPosition(int val){
        position +=val;
    }

    public String getCurrentExpression(){
        return expression.substring(position);
    }

    public Boolean isEnd(){
        return position == this.expression.length();
    }
}
