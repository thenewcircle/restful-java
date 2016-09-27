package com.example.chirp.app;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

// @ApplicationPath("/")
public class ChirpApplication extends Application {
  private Set<Class<?>> classes = new HashSet<>();

  public ChirpApplication() {
    classes.add(RootResource.class);
  }

  @Override
  public Set<Class<?>> getClasses() {
    return classes;
  }
}