package chirp.service.resources;

import java.lang.reflect.ParameterizedType;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Common base class for jerseytest classes that assumes a single servce to test
 * and logging of http traffic and dumping of entities should be enabled.
 * 
 * @author Gordon Force
 * 
 * @param <R>
 *            the jax-rs resource under test.
 */
public abstract class JerseyResourceTest<R> extends JerseyTest {

	/**
	 * Use this method to assert if an expected Status object is equivalent to
	 * the actual (observer) status code integer value in a response object.
	 * 
	 * @param expectedStatus
	 *            the expect HTTP Response status as an object
	 * @param actualResposne
	 *            the reponse object holding the observed (actual) integer
	 *            status code
	 */
	protected static void assertStatusEquals(final Status expectedStatus,
			final Response actualResponse) {
		Assert.assertEquals(expectedStatus.getStatusCode(),
				actualResponse.getStatus());
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

		// create an instance of the parameterized declared class
		@SuppressWarnings("unchecked")
		final Class<R> resourceClass = (Class<R>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		// ResourceConfig is a Jersey specific javax.ws.rs.core.Application
		// subclass
		return new ResourceConfig().packages("chirp.service.providers")
				.register(resourceClass).register(JacksonFeature.class);
	}

}
