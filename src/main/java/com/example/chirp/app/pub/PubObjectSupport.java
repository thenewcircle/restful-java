package com.example.chirp.app.pub;

import com.example.chirp.app.providers.ExceptionInfo;

public class PubObjectSupport {

  private final ExceptionInfo _status;

  public PubObjectSupport(ExceptionInfo _status) {
    this._status = _status;
  }

  public ExceptionInfo get_status() {
    return _status;
  }
}
