package chirp.service;

import java.io.IOException;
import java.net.URI;

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

	public static final int SERVER_PORT = 8080;
	public static final String SERVER_INTERFACE="0.0.0.0";
	public static final String SERVER_BIND_ADDRESS=String.format("http://%s:%d/", SERVER_INTERFACE, SERVER_PORT);
	public static final String ROOT_RESOURCE = String.format("http://localhost:%d/", SERVER_PORT);
	public static final String WADL_RESOURCE = ROOT_RESOURCE + "application.wadl";

	public static ResourceConfig createConfig() {
		/* Jersey uses java.util.logging - bridge to slf4 */
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		final ResourceConfig rc = new ResourceConfig();
		rc.packages("chirp.service.resources");
		rc.register(JacksonFeature.class);
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
				URI.create(SERVER_BIND_ADDRESS), rc);

		/* wait for shutdown ... */
		System.out.format("Jersey app started with WADL available at "
				+ "%s\nHit enter to stop it...\n\n", WADL_RESOURCE);
		System.in.read();
		httpServer.shutdownNow();

		/* save state */
		// users.freeze();
	}

}
