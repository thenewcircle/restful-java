package com.example.chirp;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.example.chirp.services.ConfigurationService;

/**
 * Lightweight, embedded HTTP server. Knows how to load and save the user
 * repository, and provide it for injection into resource classes.
 */
public class Server {

	public static final int SERVER_PORT = 8080;
	public static final String SERVER_INTERFACE="0.0.0.0";
	public static final String SERVER_BIND_ADDRESS=String.format("http://%s:%d/", SERVER_INTERFACE, SERVER_PORT);
	public static final String ROOT_RESOURCE = String.format("http://localhost:%d/", SERVER_PORT);
	public static final String WADL_RESOURCE = ROOT_RESOURCE + "application.wadl";

	public static void configureLogging() {
		/* Jersey uses java.util.logging - bridge to slf4 */
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		Logger.getLogger("org.glassfish.jersey.server.ServerRuntime$Responder").setLevel(Level.FINER);
		Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler").setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.grizzly").setLevel(Level.FINER);
	}

	public static ResourceConfig createConfig() {
		/* log additional debugging data in headers when in development. */
		final ResourceConfig rc = new ResourceConfig();
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("jersey.config.server.tracing", "ALL");
		props.put("jersey.config.server.tracing.threshold", "VERBOSE");
		rc.addProperties(props);

		/* register chrip REST resources and providers */
		rc.packages("com.example.chirp");
		rc.register(JacksonFeature.class);
		rc.register(DeclarativeLinkingFeature.class);

		return rc;
	}

	public static void main(String[] args) throws IOException {
		/* preload data into the database. */
		ConfigurationService.resetAndSeedRepository();

		/*
		 * create and start a new instance of grizzly http server exposing the
		 * Jersey application at BASE_URI
		 */
		ResourceConfig rc = createConfig();
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
				URI.create(SERVER_BIND_ADDRESS), rc);
		configureLogging();

		/* wait for shutdown ... */
		System.out.format("Jersey app started with WADL available at "
				+ "%s\nHit enter to stop it...\n\n", WADL_RESOURCE);
		System.in.read();
		httpServer.shutdownNow();
	}

}
