package com.example.chirp.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.example.chirp.app.stores.InMemoryUserStore;
import com.example.chirp.app.stores.UserStore;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

@ApplicationPath("/")
public class ChirpApplication extends Application {

	public static final UserStore USER_STORE = new InMemoryUserStore(true);

	private Set<Class<?>> classes = new HashSet<>();

	public ChirpApplication() {

		classes.add(RootResource.class);
		classes.add(GreetingsResource.class);
		classes.add(UserResource.class);
		
		classes.add(DefaultExceptionMapper.class);
		classes.add(WebApplicationExceptionMapper.class);
		classes.add(DuplicateEntityExceptionMapper.class);
		classes.add(NoSuchEntityExceptionMapper.class);
		
		classes.add(ChirpResource.class);
		classes.add(ToStringMessageBodyWriter.class);
		classes.add(FileExtensionRequestFilter.class);

		classes.add(JacksonXMLProvider.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

}