package com.example.chirp.app.resources;

import ch.qos.logback.classic.Level;
import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.stores.UserStore;
import com.example.chirp.app.support.LogbackUtil;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.core.Application;

public abstract class ResourceTestSupport extends JerseyTest {

	private Application application;

	public UserStore getUserStore() {
	    return ChirpApplication.USER_STORE;
    }

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
