package com.teamdev.calculator;

import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class GeneralTest {
    Calculator calc = new Calculator();
    @Test
    public void operand() throws BinaryOperatorException, CalculatorException{
        assertEquals("single number wasn't passed",
                BigDecimal.valueOf(5), calc.evaluate("5"));
    }
    @Test
    public void plusOperator() throws BinaryOperatorException, CalculatorException{
        assertEquals("binary operator \"+\" wasn't calculated",
                BigDecimal.valueOf(3), calc.evaluate("1+2"));
    }
    @Test
    public void minusOperator() throws BinaryOperatorException, CalculatorException{
        assertEquals("binary operator \"-\" wasn't calculated",
                BigDecimal.valueOf(-1), calc.evaluate("1-2"));
    }
    @Test
    public void divideOperator() throws BinaryOperatorException, CalculatorException{
        assertEquals("binary operator \"/\" wasn't calculated",
                BigDecimal.valueOf(3), calc.evaluate("6/2"));
    }
    @Test
    public void multiplOperator() throws BinaryOperatorException, CalculatorException{
        assertEquals("binary operator \"*\" wasn't calculated",
                BigDecimal.valueOf(12), calc.evaluate("6*2"));
    }
    @Test
    public void exp1() throws BinaryOperatorException, CalculatorException{
        assertEquals("binary operators priority don't consider",
                BigDecimal.valueOf(6), calc.evaluate("2+2*2"));
    }
    @Test
    public void exp2() throws BinaryOperatorException, CalculatorException{
        assertEquals("binary operators priority don't consider",
                BigDecimal.valueOf(-180), calc.evaluate("(10)-12/6+(10+((8-21)-9)*9)"));
    }
    @Test
    public void whitespaces() throws BinaryOperatorException, CalculatorException{
        assertEquals("whitespaces weren't calculated",
                BigDecimal.valueOf(3), calc.evaluate(" 1 + 2"));
    }

    @Test
    public void whitespaces2() throws BinaryOperatorException, CalculatorException{
        assertEquals("whitespaces weren't calculated",
                BigDecimal.valueOf(2), calc.evaluate(" 1 + 2/(1 +1)"));
    }

    //Exceptions tests

    @Test(expected = BinaryOperatorException.class)
    public void divideByZero() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("1/0");
    }
    @Test(expected = BinaryOperatorException.class)
    public void divideByZeroExp1() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("10/(2-2)");
    }
    @Test(expected = BinaryOperatorException.class)
    public void missingClosedBracket() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("((1+2)-1");
    }
    @Test(expected = BinaryOperatorException.class)
    public void missingArgumentsInBinaryOperator() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("1+1*");
    }
    @Test(expected = CalculatorException.class)
    public void missingOpenBracket() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("(1+9)-1)");
    }
    @Test(expected = CalculatorException.class)
    public void unknownOperator() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("2&2");
    }
    @Test(expected = CalculatorException.class)
    public void invalidTransition1() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("*2");
    }
    @Test(expected = CalculatorException.class)
    public void invalidTransition2() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("1*2+*2");
    }
    @Test(expected = CalculatorException.class)
    public void invalidTransition3() throws BinaryOperatorException, CalculatorException {
        calc.evaluate("1*2(2)");
    }
}
