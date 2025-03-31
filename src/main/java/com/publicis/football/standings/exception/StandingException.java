package com.publicis.football.standings.exception;

import com.publicis.football.standings.model.Error;
import lombok.Getter;

@Getter
public class StandingException extends RuntimeException {

    private final Error StandingError;

    public StandingException(String message, Error error) {
        super(message);
        this.StandingError = error;
    }

}
