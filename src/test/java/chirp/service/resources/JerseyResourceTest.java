package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestName;
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

	// Thanks to Muthu for finding this Junit 4.7 and greater feature returning
	// the name of the current method.
	@Rule
	public TestName testName = new TestName();

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
				"chirp.service.providers").register(MoxyJsonFeature.class);

	}

	/**
	 * Override this method to configure the JerseyTest client as opposed to the
	 * test server above
	 * 
	 */
	@Override
	protected void configureClient(ClientConfig config) {
		config.register(MoxyJsonFeature.class); // required to deserialize JSON
												// / XML
												// responses into Java objects
												// in the test client.
	}

	@After
	public void clearUserRepository() {
		UserRepository.getInstance().clear(); // remove data between test method
												// invocations
	}

	protected Response createUserBobStudent(Response.Status expectedStatus) {
		Form newUser = new Form().param("realname", "Bob Student").param(
				"username", "student");
		return postFormData("/users", newUser, expectedStatus);
	}

	protected Response postFormData(String uri, Form formData,
			Response.Status expectedStatus) {
		Response response = target(uri).request().post(Entity.form(formData));
		assertEquals(expectedStatus.getStatusCode(), response.getStatus());
		return response;
	}

	private Response getEntity(WebTarget target, MediaType acceptHeaderValue,
			Date lastModification, EntityTag eTag,
			Response.Status expectedResponse) {

		Invocation.Builder builder = target.request(acceptHeaderValue);

		if (lastModification != null)
			builder.header(HttpHeaders.IF_MODIFIED_SINCE, lastModification);

		if (eTag != null)
			builder.header(HttpHeaders.IF_NONE_MATCH, eTag).get();

		Response response = builder.get();

		assertEquals(expectedResponse.getStatusCode(), response.getStatus());

		return response;
	}

	private Response getEntity(WebTarget target, MediaType acceptHeaderValue,
			Response.Status expectedResponse) {
		return getEntity(target, acceptHeaderValue, null, null,
				expectedResponse);
	}

	/**
	 * Use this method to read an object from a given URL path and assert the
	 * response contains the expected status code.
	 * 
	 * @param uri
	 *            the URL path the read from
	 * @param acceptHeaderValue
	 *            the requested over the wire data format
	 * @param expectedResponse
	 *            the expected HTTP status code
	 * @return a response object containing the unread entity
	 */
	protected Response getEntity(String uri, MediaType acceptHeaderValue,
			Response.Status expectedResponse) {
		return getEntity(target(uri), acceptHeaderValue, expectedResponse);
	}

	/**
	 * Use this method to read an object from a given full URL
	 * 
	 * @param uri
	 *            the URL/ URI to read from
	 * @param acceptHeaderValue
	 *            the requested over the wire data format
	 * @param expectedResponse
	 *            the expected HTTP status code
	 * @return a response object containing the unread entity
	 */
	protected Response getEntity(URI uri, MediaType acceptHeaderValue,
			Response.Status expectedResponse) {
		return getEntity(client().target(uri), acceptHeaderValue,
				expectedResponse);
	}

	protected Response getEntity(URI uri, MediaType acceptHeaderValue,
			Date lastModification, EntityTag eTag,
			Response.Status expectedResponse) {
		return getEntity(client().target(uri), acceptHeaderValue,
				lastModification, eTag, expectedResponse);
	}

	/**
	 * Read an entity from the test server.
	 * 
	 * @param response
	 *            the object containing the unread entity
	 * @param acceptHeaderValue
	 *            the data format exchanged over the wire
	 * @param entityClass
	 *            the class type of the entity to read.
	 * @return the entity from the server.
	 */
	protected <T> T readEntity(String uri, MediaType acceptHeaderValue,
			Class<T> entityClass) {

		Response response = target(uri).request().accept(acceptHeaderValue)
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		T entity = response.readEntity(entityClass);
		assertNotNull(entity);
		return entity;
	}

	/**
	 * Extract an entity from the response object. This may only be done once
	 * per request.
	 * 
	 * @param response
	 *            the object containing the unread entity
	 * @param entityClass
	 *            the class type of the entity to read.
	 * @return the entity from the response.
	 */
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

	public Response getHead(URI uri, MediaType mediaType,
			Response.Status expectedResponse) {
		return getHead(client().target(uri), mediaType, expectedResponse);
	}

	protected Response getHead(String uriPath, MediaType mediaType,
			Response.Status expectedResponse) {
		return getHead(target(uriPath), mediaType, expectedResponse);
	}

	protected void verifyLinkHeaderExists(String relation, MediaType mediaType,
			Response response) {
		logger.info("Verify {} link is valid", relation);
		Link link = response.getLink(relation);
		assertNotNull(link);

		// link headers should be fully formed; hence, the scheme and hostname
		// must be stripped from the link header uri when using the jerseytest
		// framework.
		getHead(link.getUri(), mediaType, Response.Status.OK);
	}

}
