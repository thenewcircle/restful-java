package com.example.chirp.app;

import com.example.chirp.app.providers.AuthenticationFilter;
import com.example.chirp.app.providers.CsrfPreventionRequestFilter;
import com.example.chirp.app.providers.DeprecatedRequestFilter;
import com.example.chirp.app.providers.DuplicateEntityExceptionMapper;
import com.example.chirp.app.providers.FileExtensionRequestFilter;
import com.example.chirp.app.providers.NoSuchEntityExceptionMapper;
import com.example.chirp.app.providers.PubUserMessageBodyWriter;
import com.example.chirp.app.resources.ChirpResource;
import com.example.chirp.app.resources.HelloResource;
import com.example.chirp.app.resources.RootResource;
import com.example.chirp.app.resources.UserResource;
import com.example.chirp.kernel.stores.UsersStore;
import com.example.chirp.store.memory.InMemoryUsersStore;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChirpApplication extends Application {

  protected final Set<Class<?>> classes = new HashSet<>();
  protected final Set<Object> singletons = new HashSet<>();
  protected final Map<String, Object> properties = new HashMap<>();

  public ChirpApplication() {
    registerProperties();
    registerClasses();
  }

  private void registerProperties() {
    properties.put("jersey.config.server.tracing.type", "ALL");
    properties.put("jersey.config.server.tracing.threshold", "VERBOSE");
  
    UsersStore usersStore = new InMemoryUsersStore(true);
    properties.put(UsersStore.class.getName(), usersStore);
  }

  private void registerClasses() {
	// Resources
	classes.add(UserResource.class);
	classes.add(ChirpResource.class);
	classes.add(HelloResource.class);
	classes.add(RootResource.class);
	  
	// Filters
	classes.add(CsrfPreventionRequestFilter.class);
	classes.add(AuthenticationFilter.class);
	classes.add(FileExtensionRequestFilter.class);
	classes.add(DeprecatedRequestFilter.class);
	
	// Exception Mappers
	classes.add(DuplicateEntityExceptionMapper.class);
	classes.add(NoSuchEntityExceptionMapper.class);

	// Readers and Writers
	classes.add(JacksonXMLProvider.class);
	classes.add(PubUserMessageBodyWriter.class);
  }


  @Override
  public Set<Class<?>> getClasses() {
    return classes;
  }

  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }

  @Override
  public Map<String, Object> getProperties() {
    return properties;
  }

  public void setProperty(String name, Object value) {
    properties.put(name, value);
  }
}
