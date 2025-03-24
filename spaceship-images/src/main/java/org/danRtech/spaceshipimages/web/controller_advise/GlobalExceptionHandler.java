package org.danRtech.spaceshipimages.web.controller_advise;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles unexpected Exceptions globally.
     *
     * @param exc Exception
     * @param request WebRequest
     * @return responseEntity<Object>
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleNoSuchElementException(Exception exc, WebRequest request) {
        log.error("Caught Exception", exc);
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
        return createResponseEntity(pd, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
