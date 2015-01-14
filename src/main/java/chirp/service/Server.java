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
public class Server {
	public static final int SERVER_PORT = 8080;
	public static final String SERVER_INTERFACE="0.0.0.0";
	public static final String SERVER_BIND_ADDRESS=String.format("http://%s:%d/", SERVER_INTERFACE, SERVER_PORT);
	public static final String ROOT_RESOURCE = String.format("http://localhost:%d/", SERVER_PORT);
	public static final String WADL_RESOURCE = ROOT_RESOURCE + "application.wadl";

	public static ResourceConfig createConfig() {
		/*
		 * Log additional debugging data in headers when in development. See
		 * https://jersey.java.net/documentation/latest/monitoring_tracing.html
		 */
		final ResourceConfig rc = new ResourceConfig();
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("jersey.config.server.tracing.type", "ALL");
		props.put("jersey.config.server.tracing.threshold", "VERBOSE");
		rc.addProperties(props);

		/* register chirp REST resources and providers */
		rc.packages("chirp.service.resources");

		return rc;
	}

	public static void resetAndSeedRepository() {
		UserRepository database = UserRepository.getInstance();
		database.clear();
		database.createUser("maul", "Darth Maul");
		database.createUser("luke", "Luke Skywaler");
		database.createUser("vader", "Darth Vader");
		database.createUser("yoda", "Master Yoda");
		database.getUser("yoda").createChirp("Do or do not.  There is no try.", "wars01");
		database.getUser("yoda").createChirp("Fear leads to anger, anger leads to hate, and hate leads to suffering.","wars02");
		database.getUser("vader").createChirp("You have failed me for the last time.", "wars03");
	}

	public static void main(String[] args) throws Exception {
		/* Jersey uses java.util.logging - bridge to slf4 */
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		
		/* Start a new instance of grizzly http server. */
		ResourceConfig rc = createConfig();
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
				URI.create(SERVER_BIND_ADDRESS), rc);
		/* Enable logging of exceptions while suppressing unnecessary messages */
		Logger.getLogger("org.glassfish.grizzly").setLevel(Level.FINER);
		Logger.getLogger("org.glassfish.grizzly.nio").setLevel(Level.INFO);
		Logger.getLogger("org.glassfish.grizzly.http.io").setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler").setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.jersey.server.ServerRuntime$Responder").setLevel(Level.FINER);
		Logger.getLogger("org.glassfish.jersey.tracing").setLevel(Level.FINEST);
		
		/* Preload data into the database. */
		resetAndSeedRepository();

		/* Wait for shutdown ... */
		System.out.format("Jersey app started with WADL available at "
				+ "%s\nHit enter to stop it...\n\n", WADL_RESOURCE);
		System.in.read();
		httpServer.shutdownNow();

		/* save state */
		// users.freeze();
	}

	
}
