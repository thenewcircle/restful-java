package chirp.service;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import chirp.model.UserRepository;

/**
 * Lightweight, embedded HTTP server. Knows how to load and save the user
 * repository, and provide it for injection into resource classes.
 */
public class Server {

	public static final String BASE_URI = "http://localhost:8080/";

	private static HttpServer createServer() {

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),
				new CommonJerseyConfigurationDefault().configure());

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
