package chirp.service;

import chirp.model.UserRepository;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerUtils {

  public static final int SERVER_PORT = 8080;
  public static final String SERVER_INTERFACE="0.0.0.0";
  public static final String SERVER_BIND_ADDRESS=String.format("http://%s:%d/", SERVER_INTERFACE, SERVER_PORT);
  public static final String ROOT_RESOURCE = String.format("http://localhost:%d/", SERVER_PORT);
  public static final String WADL_RESOURCE = ROOT_RESOURCE + "application.wadl";

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

  public static void initLogging() {
  	/* Jersey uses java.util.logging - bridge to slf4 */
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

		/* Enable logging of exceptions while suppressing unnecessary messages */
    Logger.getLogger("org.glassfish.grizzly").setLevel(Level.WARNING);
    Logger.getLogger("org.glassfish.grizzly.nio").setLevel(Level.WARNING);
    Logger.getLogger("org.glassfish.grizzly.http.io").setLevel(Level.WARNING);
    Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler").setLevel(Level.WARNING);
    Logger.getLogger("org.glassfish.jersey.server.ServerRuntime$Responder").setLevel(Level.WARNING);
    Logger.getLogger("org.glassfish.jersey.tracing").setLevel(Level.WARNING);
  }
}
