package com.publicis.football.standings.exception;

import com.publicis.football.standings.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({StandingException.class})
    public ResponseEntity<Error> handleStudentNotFoundException(StandingException standingException) {
        return ResponseEntity.internalServerError().body(standingException.getStandingError());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Error> handleGenericException(Exception exception) {
        Error error = new Error();
        error.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.setMessage(exception.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }
}
