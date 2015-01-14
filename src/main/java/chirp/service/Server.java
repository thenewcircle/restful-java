package chirp.service;

import java.io.IOException;
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

	public static final String BASE_URI = "http://localhost:8080/";

	private static HttpServer createServer() {
		
		// Jersey uses java.util.logging - bridge to slf4
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		Logger.getLogger("org.glassfish.jersey.server.ServerRuntime$Responder").setLevel(Level.FINER);
		Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler").setLevel(Level.FINE);
		Logger.getLogger("org.glassfish.grizzly").setLevel(Level.FINER);

		final ResourceConfig rc = new ResourceConfig()
				.packages("chirp.service.resources");
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("jersey.config.server.tracing", "ALL");
		props.put("jersey.config.server.tracing.threshold", "VERBOSE");
		rc.addProperties(props);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),
				rc);
	}

	public static void main(String[] args) throws IOException {
	
		// final UserRepository users = UserRepository.getInstance(true);
		
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
		// users.freeze();
	}

	
}
