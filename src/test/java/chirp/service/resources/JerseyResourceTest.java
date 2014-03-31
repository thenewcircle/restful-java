package chirp.service.resources;

import java.lang.reflect.ParameterizedType;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
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
		/* enable logging of HTTP traffic */
		enable(TestProperties.LOG_TRAFFIC);

		/* enable logging of dumped HTTP traffic entities */
		enable(TestProperties.DUMP_ENTITY);

		/* Jersey uses java.util.logging - bridge to slf4 */
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		/* create an instance of the parameterized declared class */
		@SuppressWarnings("unchecked")
		final Class<R> resourceClass = (Class<R>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		/* ResourceConfig is a Jersey specific javax.ws.rs.core.Application class. */
		return new ResourceConfig().register(resourceClass);

	}

}
