package ua.balu.toyshop.exception;

public class AlreadyExistException extends IllegalArgumentException{

    private static final long serialVersionUID = 1L;
    private static final String ALREADY_EXIST = "Already exist";

    public AlreadyExistException() {
        super(ALREADY_EXIST);
    }

    public AlreadyExistException(String message) {
        super(message.isEmpty()?ALREADY_EXIST:message);
    }
}
