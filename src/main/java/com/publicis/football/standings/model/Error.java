package com.publicis.football.standings.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Error {
    @JsonProperty("error")
    private String error;
    @JsonProperty("message")
    private String message;
}
