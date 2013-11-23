package com.teamdev.calculator;

import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class GeneralTest {
    Calculator calc = new Calculator();
    @Test
    public void operand() {
        assertEquals("single number wasn't passed",
                BigDecimal.valueOf(5), calc.evaluate("5"));
    }
    @Test
    public void plusOperator() {
        assertEquals("binary operator \"+\" wasn't calculated",
                BigDecimal.valueOf(3), calc.evaluate("1+2"));
    }
    @Test
    public void minusOperator() {
        assertEquals("binary operator \"-\" wasn't calculated",
                BigDecimal.valueOf(-1), calc.evaluate("1-2"));
    }
    @Test
    public void divideOperator() {
        assertEquals("binary operator \"/\" wasn't calculated",
                BigDecimal.valueOf(3), calc.evaluate("6/2"));
    }
    @Test
    public void multiplOperator() {
        assertEquals("binary operator \"*\" wasn't calculated",
                BigDecimal.valueOf(12), calc.evaluate("6*2"));
    }
    @Test
    public void exp1() {
        assertEquals("binary operators priority don't consider",
                BigDecimal.valueOf(6), calc.evaluate("2+2*2"));
    }
    @Test
    public void exp2() {
        assertEquals("brackets weren't calculated",
                BigDecimal.valueOf(-180), calc.evaluate("(10)-12/6+(10+((8-21)-9)*9)"));
    }
}
