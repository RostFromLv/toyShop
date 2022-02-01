package ua.balu.toyshop.exception.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.balu.toyshop.dto.exception.ExceptionResponse;
import ua.balu.toyshop.exception.*;


@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public final ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException exception){

        return buildExceptionBody(exception,HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler(IncorrectInputException.class)
    public final ResponseEntity<Object> handleIncorrectInputException(IncorrectInputException exception){
        return buildExceptionBody(exception,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DatabaseRepositoryException.class)
    public final ResponseEntity<Object> handlerDatabaseRepositoryException(DatabaseRepositoryException exception){
        return buildExceptionBody(exception,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotExistException.class)
    public final ResponseEntity<Object> handlerNotExistException(NotExistException exception){
        return buildExceptionBody(exception,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JwtAuthenticationException.class)
    public final ResponseEntity<Object> handlerJwtAuthenticationException(JwtAuthenticationException exception){
        return buildExceptionBody(exception,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IncorrectActionException.class)
    public final ResponseEntity<Object> handlerIncorrectActionException(IncorrectActionException exception){
        return buildExceptionBody(exception,HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        StringBuilder sb = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach((error) -> {
            sb.append(error.getField()).append(" ").append(error.getDefaultMessage()).append(" and ");
        });
        sb.setLength(sb.length() - 5);
        return buildExceptionBody(new IncorrectInputException(sb.toString()), status);
    }

    private ResponseEntity<Object> buildExceptionBody(Exception exception, HttpStatus status){

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .status(status.value())
                .build();
        log.debug(exception.getMessage());
        return ResponseEntity.status(status)
                .body(exceptionResponse);
    }


}
