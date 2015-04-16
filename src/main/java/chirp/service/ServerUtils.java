package chirp.service;

import chirp.model.UserRepository;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.HashMap;
import java.util.Map;

public class ServerUtils {

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
    rc.packages("chirp.service.resources",
                "chirp.service.providers");

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
    database.getUser("yoda").createChirp("Fear leads to anger, anger leads to hate, and hate leads to suffering.", "wars02");
    database.getUser("vader").createChirp("You have failed me for the last time.", "wars03");
  }
}
