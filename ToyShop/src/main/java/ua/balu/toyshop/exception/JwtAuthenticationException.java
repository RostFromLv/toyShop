package ua.balu.toyshop.exception;


import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException  extends AuthenticationException {
    private final static String AUTHENTICATION_EXCEPTION = "Token is invalid for now";

    public JwtAuthenticationException(String explanation) {
        super(explanation.isEmpty()?AUTHENTICATION_EXCEPTION:explanation);
    }

    public JwtAuthenticationException() {
        super(AUTHENTICATION_EXCEPTION);
    }
}
