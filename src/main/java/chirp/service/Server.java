package chirp.service;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import chirp.model.UserRepository;

/**
 * Lightweight, embedded HTTP server. Knows how to load and save the user
 * repository, and provide it for injection into resource classes.
 */
public class Server {

	public static final String BASE_URI = "http://localhost:8080/";

	private static HttpServer createServer() {

		final ResourceConfig rc = new ResourceConfig().packages(
				"chirp.service.resources;chirp.service.providers").register(JacksonFeature.class);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI

		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
				URI.create(BASE_URI), rc);

		return server;

	}
	

	public static void main(String[] args) throws IOException {

		final UserRepository users = UserRepository.getInstance(true);

		// wait for shutdown ...
		HttpServer httpServer = createServer();

		System.out.println(String.format(
				"Jersey app started with WADL available at "
						+ "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));

		// System.out.println("Hit <return> to stop server...");
		System.in.read();
		httpServer.shutdownNow();

		// save state
		users.freeze();
	}

}
