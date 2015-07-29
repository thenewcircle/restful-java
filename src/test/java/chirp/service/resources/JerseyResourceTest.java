package chirp.service.resources;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import chirp.model.UserRepository;

/**
 * Common base class for jerseytest classes that assumes a single servce to test
 * and logging of http traffic and dumping of entities should be enabled.
 * 
 * @author Gordon Force
 * 
 * @param <R>
 *            the jax-rs resource under test.
 */
public abstract class JerseyResourceTest extends JerseyTest {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Response addStudentUser() {
		return addAnyUser("student", "bob");
	}

	protected Response addAnyUser(String username, String realname) {
		Response response = target("/users").request().post(
				Entity.form(new Form().param("username", username).param(
						"realname", realname)));
		assertNotNull(response);
		return response;
	}

	@Before
	public void setup() {
		UserRepository.getInstance().clear();
	}

	/**
	 * Call this method to recreate a jersey test runtime with the following
	 * configuration changes to the default.
	 * <ul>
	 * <li>Enabled logging of HTTP traffic to the STDERR device.</li>
	 * <li>Enabled dumping of HTTP traffic entities to the STDERR device.</li>
	 * <li>Registered the resource specific as the generic type argument with
	 * the Jersey runtime.</li>
	 * <li>Registered all provides declared in the
	 * <code>chirp.service.providers</code> package.</li>
	 * </ul>
	 */
	@Override
	protected Application configure() {
		// enable logging of HTTP traffic
		enable(TestProperties.LOG_TRAFFIC);

		// enable logging of dumped HTTP traffic entities
		enable(TestProperties.DUMP_ENTITY);

		// Jersey uses java.util.logging - bridge to slf4
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		// ResourceConfig is a Jersey specific javax.ws.rs.core.Application
		// subclass
		return new ResourceConfig().packages("chirp.service.resources",
				"chirp.service.providers").register(JacksonFeature.class);
	}

	/**
	 * Override this method to configure the JerseyTest client as opposed to the
	 * test server above
	 * 
	 */
	@Override
	protected void configureClient(ClientConfig config) {
		config.register(JacksonFeature.class); // required to deserialize JSON
												// responses into Java objects
												// in the test client.
	}

}
