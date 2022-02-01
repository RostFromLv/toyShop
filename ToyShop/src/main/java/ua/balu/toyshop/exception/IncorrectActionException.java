package ua.balu.toyshop.exception;

public class IncorrectActionException  extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private final static String ACCESS_FORBIDDEN = "You cant do this action";

    public IncorrectActionException() {
        super(ACCESS_FORBIDDEN);
    }

    public IncorrectActionException(String message) {
        super(message.isEmpty()?ACCESS_FORBIDDEN:message);
    }

}
