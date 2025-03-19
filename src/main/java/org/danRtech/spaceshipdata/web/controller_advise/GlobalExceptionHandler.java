package org.danRtech.spaceshipdata.web.controller_advise;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handling NoSuchElementException globally.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exc, WebRequest request){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getMessage());
        return createResponseEntity(problemDetail, null, HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handling ResourceNotFoundExceptions globally.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exc, WebRequest request){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getMessage());
        return createResponseEntity(problemDetail, null, HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handling all other unexpected exceptions globally.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleUnexpectedException(Exception exc, WebRequest request){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
        return createResponseEntity(problemDetail, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
