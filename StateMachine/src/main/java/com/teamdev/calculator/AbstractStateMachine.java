package com.teamdev.calculator;

abstract public class AbstractStateMachine<Result,
        GeneralException extends Exception,
        ExceptionWithPosition extends Exception,
        Reader extends AbstractReader,
        States extends Enum,
        TransitionMatrix extends AbstractTransitionMatrix<States>,
        ExpressionStack extends AbstractExpressionStack,
        Recognizer extends AbstractRecognizer<States, Reader, ExpressionStack, GeneralException, ExceptionWithPosition>> {
    public Result run(Reader expressionReader) throws GeneralException, ExceptionWithPosition {
        TransitionMatrix trMatrix = getTransitionMatrix();
        States state = trMatrix.getStartState();

        while (!expressionReader.isEnd()) {
            state =evaluateState(state, expressionReader);
            if (null == state) {
                stateException(expressionReader);
            }
        }
        return getResult();
    }

    private States evaluateState(States states, Reader expressionReader) throws GeneralException, ExceptionWithPosition{
        TransitionMatrix trMatrix = getTransitionMatrix();
        States[] listState = trMatrix.getTransition(states);
        States currentState;
        Recognizer recognizers = getRecognizer();
        for (int i = 0; i < listState.length; i++) {
            currentState = listState[i];
            if (null != recognizers.accept(currentState, expressionReader)) {
                return currentState;
            }
        }
        return null;
    }

    abstract protected void stateException(Reader expressionReader) throws ExceptionWithPosition;

    abstract protected ExpressionStack getExpressionStack();

    abstract protected TransitionMatrix getTransitionMatrix();

    abstract protected Recognizer getRecognizer();

    abstract protected Result getResult() throws ExceptionWithPosition, GeneralException;
}
