package chirp.service.resources;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import chirp.service.CommonJerseyConfigurationDefault;
import chirp.service.representations.Representation;

/**
 * Common base class for jerseytest classes that assumes a single servce to test
 * and logging of http traffic and dumping of entities should be enabled.
 * 
 * @author Gordon Force
 * 
 */
public abstract class JerseyResourceTest extends JerseyTest {
	
	final protected ResourceTestClient<? extends Representation, ? extends Representation> testResourceClient;
	
	protected JerseyResourceTest() {
		this(null);
	}

	protected JerseyResourceTest(Class<?> resourceClass) {
		testResourceClient = (resourceClass == null) ? null
				: ResourceTestClientFactory.Default.getInstance().create(
						resourceClass, this);
	}

	/**
	 * Call this method to recreate a jersey test server's runtime with the
	 * following configuration changes to the default.
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

		return new CommonJerseyConfigurationDefault().configure();
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

	protected MediaType getDefaultMediaType() {
		return MediaType.APPLICATION_XML_TYPE;
	}

}
