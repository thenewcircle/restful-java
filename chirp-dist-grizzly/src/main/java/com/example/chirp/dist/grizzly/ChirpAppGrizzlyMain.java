package com.example.chirp.dist.grizzly;

import ch.qos.logback.classic.Level;
import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.LogbackUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.awt.*;
import java.net.URI;

public class ChirpAppGrizzlyMain {

	private static final int SERVER_PORT = 8080;
	private static final String SERVER_INTERFACE = "0.0.0.0";
	private static final String SERVER_BIND_ADDRESS = String.format("http://%s:%d/chirp", SERVER_INTERFACE, SERVER_PORT);
	private static final String ROOT_RESOURCE = String.format("http://localhost:%d/chirp/", SERVER_PORT);
	private static final String WADL_RESOURCE = ROOT_RESOURCE + "application.wadl";

	public static void main(String[] args) throws Exception {
		LogbackUtil.initLogback(Level.WARN);

		// Create the application and then Jersey's ResourceConfig
		ChirpApplication chirpApplication = new ChirpApplication();

    // Start a new instance of grizzly http server.
    URI baseUri = URI.create(SERVER_BIND_ADDRESS);
		ResourceConfig resourceConfig = ResourceConfig.forApplication(chirpApplication);
    HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);

		Desktop.getDesktop().browse(URI.create(ROOT_RESOURCE));

		// Wait for shutdown ...
		System.out.format("Grizzly HTTP Server started with WADL available at %s\nHit enter to stop it...\n\n", WADL_RESOURCE);
		System.in.read();

		httpServer.shutdownNow();
	}
}
