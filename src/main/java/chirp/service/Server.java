package chirp.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

import chirp.model.UserRepository;

/**
 * Lightweight, embedded HTTP server. Knows how to load and save the user
 * repository, and provide it for injection into resource classes.
 */
public class Server {

	/** 0.0.0.0 binds to both localhost and your public IP address. */
	public static final String BASE_URI = "http://0.0.0.0:8080/";

	public static ResourceConfig createConfig() {
		/* Jersey uses java.util.logging - bridge to slf4 */
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		Logger.getLogger("org.glassfish.jersey.server.ServerRuntime$Responder")
				.setLevel(Level.FINER);

		final ResourceConfig rc = new ResourceConfig();
		rc.packages("chirp.service.resources");
		rc.register(JacksonFeature.class);
		// Map<String,Object> props = new HashMap<String,Object>();
		// props.put("jersey.config.server.tracing", "ALL");
		// props.put("jersey.config.server.tracing.threshold", "VERBOSE");
		// rc.addProperties(props);
		return rc;
	}

	public static void main(String[] args) throws IOException {

		/* preload data into the database. */
		final UserRepository users = UserRepository.getInstance();
		// users.thaw();
		users.prepopulate();

		/*
		 * create and start a new instance of grizzly http server exposing the
		 * Jersey application at BASE_URI
		 */
		ResourceConfig rc = createConfig();
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
				URI.create(BASE_URI), rc, false);
		Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler")
				.setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.grizzly").setLevel(Level.FINER);
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		Logger.getLogger("org.glassfish.jersey.server.ServerRuntime$Responder")
				.setLevel(Level.FINER);
		httpServer.getServerConfiguration().setTraceEnabled(true);
		System.err.println(httpServer.getServerConfiguration()
				.getDefaultErrorPageGenerator().getClass().getName());
		httpServer.start();

		/* wait for shutdown ... */
		System.out.format("Jersey app started with WADL available at "
				+ "%sapplication.wadl\nHit enter to stop it...\n\n", BASE_URI);
		System.in.read();
		httpServer.shutdownNow();

		/* save state */
		// users.freeze();
	}

}
