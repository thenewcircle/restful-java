package com.example.chirp.app.resources;

import javax.ws.rs.core.Application;

import ch.qos.logback.classic.Level;
import com.example.chirp.app.LogbackUtil;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

public abstract class ResourceTestSupport extends JerseyTest {

	@Override
	protected Application configure() {
    LogbackUtil.initLogback(Level.WARN);

		return new ResourceConfig().packages("com.example.chirp.app");
	}
}
