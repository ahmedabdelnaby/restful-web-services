package com.in28minutes.webservices.restfulwebservices.exception;

import com.in28minutes.webservices.restfulwebservices.user.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDate;

@ControllerAdvice // to make this class picked up by the spring to apply with every request
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * - '@ExceptionHandler(Exception.class)' -> what type of exceptions this method will handle.
     * - ResponseEntity<ErrorDetails> -> what is the structure of the response will be returned 'ErrorDetails'.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
                                                        ex.getMessage(), request.getDescription(false));

        // ResponseEntity<theStructureOfResponse>(theResponseObject, theHTTPCodeWillBeReturned);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
                                                        ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
                                        MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                HttpStatusCode statusCode, WebRequest request) {
        ErrorDetails errorDetails =
                        new ErrorDetails(LocalDate.now(),
                                ex.getFieldError().getDefaultMessage(), // to get the message that defined on the entity validation
                                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}