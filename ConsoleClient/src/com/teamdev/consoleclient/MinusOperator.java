package com.teamdev.consoleclient;

import java.math.BigDecimal;

public class MinusOperator extends AbstractBinaryOperator {
    public MinusOperator(int priority) {
        super(priority);
    }
    //test
    public String getName(){
        return "-";
    }
    public BigDecimal evaluate(BigDecimal first, BigDecimal second) {
        return second.subtract(first);
    }
}
