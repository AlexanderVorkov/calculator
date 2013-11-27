package com.teamdev.calculator;
public interface AbstractRecognizer<
        States extends Enum,
        Reader extends AbstractReader,
        ExpressionStack extends AbstractExpressionStack,
        GeneralException extends Exception,
        ExceptionWithPosition extends Exception> {
    public Boolean accept(States states, Reader reader) throws GeneralException, ExceptionWithPosition;
}
