package com.teamdev.consoleclient;

import java.math.BigDecimal;

public interface BinaryOperator extends Comparable<BinaryOperator>{
    public BigDecimal evaluate(BigDecimal first, BigDecimal second);
    public int getPriority();
    public String getName();
}
