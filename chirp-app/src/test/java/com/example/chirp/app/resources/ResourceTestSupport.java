package com.example.chirp.app.resources;

import javax.ws.rs.core.Application;

import ch.qos.logback.classic.Level;
import com.example.chirp.app.LogbackUtil;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.slf4j.bridge.SLF4JBridgeHandler;

public abstract class ResourceTestSupport extends JerseyTest {

	@Override
	protected Application configure() {
    LogbackUtil.initLogback(Level.WARN);

		return new ResourceConfig().packages("com.example.chirp.app");
	}
}
