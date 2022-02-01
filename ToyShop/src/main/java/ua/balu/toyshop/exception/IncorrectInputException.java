package ua.balu.toyshop.exception;


public class IncorrectInputException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;
    private static final String INCORRECT_INPUT_MESSAGE = "Incorrect input";

    public IncorrectInputException() {
    }

    public IncorrectInputException(String message) {
        super(message.isEmpty() ? INCORRECT_INPUT_MESSAGE : message);
    }
}
