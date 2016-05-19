package com.example.chirp.app.providers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionInfo {
 
  private final int status;
  private final String message;

  @JsonCreator
  public ExceptionInfo(@JsonProperty("status") int status,
                       @JsonProperty("message") String message) {
    this.status = status;
    this.message = message;
  }
  public int getStatus() { return status; }
  public String getMessage() { return message; }
}