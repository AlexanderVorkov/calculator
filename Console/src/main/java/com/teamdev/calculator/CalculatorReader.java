package com.teamdev.calculator;

public class CalculatorReader {
    private String expression;
    private int position = 0;

    public CalculatorReader(String expression){
        this.expression = expression;
    }

    public char getChar(){
        return expression.charAt(position);
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
