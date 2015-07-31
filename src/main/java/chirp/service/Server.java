package chirp.service;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Lightweight, embedded HTTP server.
 */
public class Server {
	private static final int SERVER_PORT = 8080;
	private static final String SERVER_INTERFACE = "0.0.0.0";
	private static final String SERVER_BIND_ADDRESS = String.format(
			"http://%s:%d/", SERVER_INTERFACE, SERVER_PORT);
	private static final String ROOT_RESOURCE = String.format(
			"http://localhost:%d/", SERVER_PORT);
	private static final String WADL_RESOURCE = ROOT_RESOURCE
			+ "application.wadl";

	private static HttpServer createServer() {

		/* Jersey uses java.util.logging - bridge to slf4 */
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		/* Start a new instance of grizzly http server. */
		final HttpServer httpServer = GrizzlyHttpServerFactory
				.createHttpServer(
						URI.create(SERVER_BIND_ADDRESS),
						new ResourceConfig()
								.packages("chirp.service.resources",
										"chirp.service.providers")
								.register(JacksonFeature.class)
								.property(
										ServerProperties.BV_SEND_ERROR_IN_RESPONSE,
										true));
		/*
		 * Enable for request based tracing returned as link headers
		 * .property(ServerProperties.TRACING, "ALL")
		 * .property(ServerProperties.TRACING_THRESHOLD, "ALL"));
		 */
		
		/*
		 * Log additional debugging data in headers when in development. See
		 * https://jersey.java.net/documentation/latest/monitoring_tracing.html
		 */
		/* Enable logging of exceptions while suppressing unnecessary messages */
		Logger.getLogger("org.glassfish.grizzly").setLevel(Level.FINER);
		Logger.getLogger("org.glassfish.grizzly.nio").setLevel(Level.INFO);
		Logger.getLogger("org.glassfish.grizzly.http.io").setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler")
				.setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.jersey.server.ServerRuntime$Responder")
				.setLevel(Level.FINER);
		Logger.getLogger("org.glassfish.jersey.tracing").setLevel(Level.FINEST);

		return httpServer;

	}

	public static void main(String[] args) throws Exception {

		final HttpServer httpServer = createServer();

		/* Wait for shutdown ... */
		System.out.format("Jersey app started with WADL available at "
				+ "%s\nHit enter to stop it...\n\n", WADL_RESOURCE);
		
		System.in.read();
		
		httpServer.shutdownNow();
	}
}
