package com.example.chirp.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

@ApplicationPath("/")
public class ChirpApplication extends Application {

	private Set<Class<?>> classes = new HashSet<>();

	public ChirpApplication() {
		classes.add(JacksonXMLProvider.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

}