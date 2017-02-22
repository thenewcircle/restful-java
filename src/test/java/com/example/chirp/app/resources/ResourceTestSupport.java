package com.example.chirp.app.resources;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;

import ch.qos.logback.classic.Level;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.support.LogbackUtil;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

public abstract class ResourceTestSupport extends JerseyTest {

	private Application application;

	@Override
	protected Application configure() {
		 LogbackUtil.initLogback(Level.WARN);
		application = new ChirpApplication();
		return application;
	}

	@Override
	protected void configureClient(ClientConfig config) {
		config.register(JacksonXMLProvider.class);
		super.configureClient(config);
	}
}
