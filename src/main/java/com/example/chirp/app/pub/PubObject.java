package com.example.chirp.app.pub;

import java.net.URI;
import java.util.Map;

import com.example.chirp.app.providers.ExceptionInfo;

public interface PubObject {

  public ExceptionInfo get_status();
  public Map<String,URI> get_links();
  
}
