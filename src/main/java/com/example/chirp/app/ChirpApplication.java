package com.example.chirp.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

@Component
public class ChirpApplication extends Application {

  private Set<Class<?>> classes = new HashSet<>();

  public ChirpApplication() {
  
	classes.add(JacksonXMLProvider.class);
	  
//	classes.add(RootResource.class);
//	classes.add(GreetingsResource.class);
//	classes.add(UserResource.class);
//	classes.add(DuplicateEntityExceptionMapper.class);

  }

  @Override
  public Set<Class<?>> getClasses() {
    return classes;
  }
}