package com.teamdev.consoleclient;

import java.math.BigDecimal;

public class MultiplOperator extends AbstractBinaryOperator {

    public MultiplOperator(int priority) {
        super(priority);
    }
    //test
    public String getName(){
        return "*";
    }
    public BigDecimal evaluate(BigDecimal first, BigDecimal second) {
        return first.multiply(second);
    }
}
