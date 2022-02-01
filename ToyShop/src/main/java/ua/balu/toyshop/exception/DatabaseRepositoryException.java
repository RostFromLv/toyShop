package ua.balu.toyshop.exception;


public class DatabaseRepositoryException extends RuntimeException{
    public static final long serialVersionUID =1l;
    public static final String REPOSITORY_EXCEPTION = "Database repository eception";

    public DatabaseRepositoryException() {
        super(REPOSITORY_EXCEPTION);
    }

    public DatabaseRepositoryException(String message) {
        super(message.isEmpty()?REPOSITORY_EXCEPTION:message);
    }
}
