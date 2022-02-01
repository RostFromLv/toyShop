package ua.balu.toyshop.exception;

public class NotExistException extends IllegalArgumentException{
    public static final long serialVersionUID = 1l;
    public static final String NOT_EXIST_EXCEPION = "Not exist";

    public NotExistException() {
        super(NOT_EXIST_EXCEPION);
    }

    public NotExistException(String message) {
        super(message.isEmpty()?NOT_EXIST_EXCEPION:message);
    }
}
