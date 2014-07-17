package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.ParameterizedType;
import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());


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
		return new ResourceConfig().register(resourceClass)
				.packages("chirp.service.providers")
				.register(JacksonFeature.class);

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

	protected Response postFormData(String uri, Form formData,
			Response.Status expectedResponse) {
		Response response = target(uri).request().post(Entity.form(formData));
		assertEquals(expectedResponse.getStatusCode(), response.getStatus());
		return response;
	}

	private Response getEntity(WebTarget target, MediaType acceptHeaderValue,
			Response.Status expectedResponse) {
		Response response = target.request(acceptHeaderValue).get();
		assertEquals(expectedResponse.getStatusCode(), response.getStatus());
		return response;
	}

	protected Response getEntity(String uri, MediaType acceptHeaderValue,
			Response.Status expectedResponse) {
		return getEntity(target(uri), acceptHeaderValue, expectedResponse);
	}

	protected Response getEntity(URI uri, MediaType acceptHeaderValue,
			Response.Status expectedResponse) {
		return getEntity(client().target(uri), acceptHeaderValue,
				expectedResponse);
	}

	protected <T> T readEntity(String uri, MediaType acceptHeaderValue,
			Class<T> entityClass) {

		Response response = target(uri).request().accept(acceptHeaderValue)
				.get();
		assertEquals(Response.Status.OK, response.getStatus());
		T entity = response.readEntity(entityClass);
		assertNotNull(entity);
		return entity;
	}

	protected <T> T readEntity(Response response, Class<T> entityClass) {

		T entity = response.readEntity(entityClass);
		assertNotNull(entity);
		return entity;
	}

}
