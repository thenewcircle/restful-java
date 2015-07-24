package com.example.chirp.app.resources;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import ch.qos.logback.classic.Level;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.stores.UserStore;
import com.example.chirp.app.support.LogbackUtil;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

public abstract class ResourceTestSupport extends JerseyTest {

	public static UserStore userStore;
	
	@Override
	protected Application configure() {
		LogbackUtil.initLogback(Level.WARN);

		// This configures Spring to use the "test" profile.
		System.getProperties().setProperty("spring.profiles.active", "test");

		ChirpApplication application = new ChirpApplication();
		ResourceConfig resourceConfig = ResourceConfig.forApplication(application);
		resourceConfig.packages("com.example.chirp.app");

		// Indicates to Jersey the location for the spring-config file.
		return resourceConfig.property("contextConfigLocation", "classpath:/chirp-test-spring.xml");	
	}

	@Override
	protected void configureClient(ClientConfig config) {
		config.register(JacksonXMLProvider.class);
		super.configureClient(config);
	}

	public UserStore getUserStore() {
		return userStore;
	}
}
