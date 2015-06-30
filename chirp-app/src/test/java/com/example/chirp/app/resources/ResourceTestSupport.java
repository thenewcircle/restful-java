package com.example.chirp.app.resources;

import javax.ws.rs.core.Application;

import ch.qos.logback.classic.Level;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.LogbackUtil;
import com.example.chirp.kernel.stores.UsersStore;

import org.glassfish.jersey.test.JerseyTest;

public abstract class ResourceTestSupport extends JerseyTest {

	private Application application;
	
	@Override
	protected Application configure() {
		LogbackUtil.initLogback(Level.WARN);
		application = new ChirpApplication();
		return application;
	}
	
	public UsersStore getUserStore() {
		return (UsersStore)application.getProperties().get(UsersStore.class.getName());
	}
}
