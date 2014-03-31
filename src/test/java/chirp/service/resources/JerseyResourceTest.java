package chirp.service.resources;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import chirp.service.Server;

/**
 * Common base class for Jersey test classes that assumes a single service to test
 * and logging of HTTP traffic and dumping of entities should be enabled.
 */
public abstract class JerseyResourceTest extends JerseyTest {

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

		/* ResourceConfig is a Jersey specific javax.ws.rs.core.Application class. */
		ResourceConfig rc = Server.createConfig();
		return rc;
	}

}
