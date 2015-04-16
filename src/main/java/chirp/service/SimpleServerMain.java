package chirp.service;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.simple.SimpleContainerFactory;
import org.glassfish.jersey.simple.SimpleServer;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class SimpleServerMain {

  public static void main(String...args) throws Exception {
    URI baseUri = URI.create(ServerUtils.SERVER_BIND_ADDRESS);
    ResourceConfig config = ServerUtils.createConfig();
    SimpleServer server = SimpleContainerFactory.create(baseUri, config);

		/* Preload data into the database. */
    ServerUtils.resetAndSeedRepository();

		/* Wait for shutdown ... */
    System.out.format("Jersey app started with WADL available at %s\nHit enter to stop it...\n\n", ServerUtils.WADL_RESOURCE);
    System.in.read();
  }
}
