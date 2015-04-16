package chirp.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

import chirp.model.UserRepository;

/**
 * Lightweight, embedded HTTP server. Knows how to load and save the user
 * repository, and provide it for injection into resource classes.
 */
public class GrizzlyServerMain {

	public static void main(String[] args) throws Exception {
		/* Jersey uses java.util.logging - bridge to slf4 */
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		
		/* Start a new instance of grizzly http server. */
		URI baseUri = URI.create(ServerUtils.SERVER_BIND_ADDRESS);
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, ServerUtils.createConfig());

		/* Enable logging of exceptions while suppressing unnecessary messages */
		Logger.getLogger("org.glassfish.grizzly").setLevel(Level.FINER);
		Logger.getLogger("org.glassfish.grizzly.nio").setLevel(Level.INFO);
		Logger.getLogger("org.glassfish.grizzly.http.io").setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler").setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.jersey.server.ServerRuntime$Responder").setLevel(Level.FINER);
		Logger.getLogger("org.glassfish.jersey.tracing").setLevel(Level.FINEST);
		
		/* Preload data into the database. */
		ServerUtils.resetAndSeedRepository();

		/* Wait for shutdown ... */
		System.out.format("Jersey app started with WADL available at %s\nHit enter to stop it...\n\n", ServerUtils.WADL_RESOURCE);
		System.in.read();

		httpServer.shutdownNow();
	}
}
