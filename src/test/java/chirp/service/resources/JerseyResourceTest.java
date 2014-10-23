package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
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
 */
public abstract class JerseyResourceTest extends JerseyTest {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

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
				"chirp.service.providers");

	}

	/**
	 * Override this method to configure the JerseyTest client as opposed to the
	 * test server above
	 * 
	 */
	@Override
	protected void configureClient(ClientConfig config) {
		config.register(MoxyJsonFeature.class); // required to deserialize JSON
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
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		T entity = response.readEntity(entityClass);
		assertNotNull(entity);
		return entity;
	}

	protected <T> T readEntity(Response response, Class<T> entityClass) {

		T entity = response.readEntity(entityClass);
		assertNotNull(entity);
		return entity;
	}

	private Response getHead(WebTarget target, MediaType mediaType,
			Response.Status expectedResponse) {
		Response response = target.request(mediaType).head();
		assertEquals(expectedResponse.getStatusCode(), response.getStatus());
		return response;
	}

	protected Response getHead(URI uri, MediaType mediaType,
			Response.Status expectedResponse) {
		return getHead(client().target(uri), mediaType, expectedResponse);
	}

	protected Response getHead(String uriPath, MediaType mediaType,
			Response.Status expectedResponse) {
		return getHead(target(uriPath), mediaType, expectedResponse);
	}

	protected void verifyLinkHeaderExists(String relation, MediaType mediaType,
			Response response) {
		log.info("Verify {} link is valid", relation);
		Link link = response.getLink(relation);
		assertNotNull(link);

		// link headers should be fully formed; hence, the scheme and hostname
		// must be stripped from the link header uri when using the jerseytest
		// framework.
		getHead(link.getUri(), mediaType, Response.Status.OK);
	}

}
