package com.example.chirp.app.pub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ExceptionMapper;

public class ExceptionInfo {
    private final int status;
    private final String message;

    public ExceptionInfo(WebApplicationException e) {
        this.status = e.getResponse().getStatus();

        this.message = e.getMessage() == null ?
            e.getClass().getName() :
            e.getMessage();
    }

    @JsonCreator
    public ExceptionInfo(@JsonProperty("status") int status,
                         @JsonProperty("message") String message) {
        this.status = status;
        this.message = message;
    }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
}
