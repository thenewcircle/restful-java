package chirp.service.resources.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;

import chirp.service.representations.CollectionRepresentation;
import chirp.service.representations.Representation;
import chirp.service.resources.ResourceTestClient;

/**
 * Use this class to provide common implementations of entity client operations
 * assuming a fixed set of data.
 * 
 * @author Gordon Force
 * 
 * @param <R>
 *            the resource under test
 * @param <P>
 *            the representation class under test
 * @param <CP>
 *            the collections entity resource under test
 */
public abstract class AbstractEntityClientImpl<R, P extends Representation, CP extends CollectionRepresentation<P>>
		implements ResourceTestClient<P, CP> {

	private Type getTypeClass(final int index) {
		return ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[index];
	}

	@SuppressWarnings("unchecked")
	private final Class<R> resourceClass = (Class<R>) getTypeClass(0);

	@SuppressWarnings("unchecked")
	private final Class<P> represntationClass = (Class<P>) getTypeClass(1);

	@SuppressWarnings("unchecked")
	private final Class<CP> entityCollectionClass = (Class<CP>) getTypeClass(2);

	private final JerseyTest jerseyTest;

	public AbstractEntityClientImpl(JerseyTest jerseyTest) {
		this.jerseyTest = jerseyTest;
	}

	public JerseyTest getJerseyTest() {
		return jerseyTest;
	}

	protected Response createWithStatusInternal(final Form postForm,
			final Status expectedStatus) {

		return createWithStatusInternal(postForm,
				UriBuilder.fromResource(resourceClass).build(), expectedStatus);

	}

	protected Response createWithStatusInternal(final Form postForm,
			URI location, final Status expectedStatus) {

		// http://localhost:8080/users,
		// grizzly and jersey imemory servers only deal with paths, or what is
		// to the right of the hostname.

		final Response response = jerseyTest.target(location.getPath()) // note:
																		// target
																		// method
																		// only
																		// accepts
																		// paths
																		// that
																		// are
																		// strings.
				.request().post(Entity.form(postForm));
		assertStatusEquals(expectedStatus, response);
		return response;
	}

	@Override
	public P createWithGetLocationVerify(MediaType mediaType) {
		Response response = createWithStatus(Status.CREATED);
		return get(response.getLocation(), mediaType);
	}

	@Override
	public Response createWithHeadLocationVerify(MediaType mediaType) {
		Response response = createWithStatus(Status.CREATED);
		Response getResponse = getWithStatus(response.getLocation(), mediaType,
				Status.OK);
		return getResponse;
	}

	@Override
	public Response getWithStatus(final URI location, MediaType mediaType,
			final Status expectedStatus) {
		final Response response = jerseyTest.target(location.getPath())
				.request(mediaType).get();
		assertStatusEquals(expectedStatus, response);
		return response;
	}

	@Override
	public P get(final URI location, MediaType mediaType) {
		final P entity = jerseyTest.target(location.getPath())
				.request(mediaType).get(represntationClass);
		assertNotNull(entity);
		return entity;
	}

	protected abstract String createEntityCollectionURIPath();

	@Override
	public CP getAll(final MediaType mediaType) {
		final String uriPath = createEntityCollectionURIPath();
		final Response response = getJerseyTest().target(uriPath)
				.request(mediaType).get();

		assertStatusEquals(Status.OK, response);
		assertTrue(response.hasEntity());

		// @SuppressWarnings("unchecked")
		final CP collection = (CP) response.readEntity(entityCollectionClass);
		assertNotNull(collection);
		assertNotNull(collection.getSelf());
		assertEquals(uriPath, collection.getSelf().toString());

		return collection;
	}

	protected CP getAllTestInternal(Collection<Form> forms, MediaType mediaType) {

		for (Form form : forms) {
			createWithStatusInternal(form, Status.CREATED);
		}

		CP result = getAll(mediaType);
		assertEquals(forms.size(), result.get().size());

		return result;
	}

	public Class<R> getResourceClass() {
		return resourceClass;
	}

	public Class<P> getEntityClass() {
		return represntationClass;
	}

	public Class<CP> getEntityCollectionClass() {
		return entityCollectionClass;
	}

	/**
	 * Use this method to assert if an expected Status object is equivalent to
	 * the actual (observer) status code integer value in a response object.
	 * 
	 * @param expectedStatus
	 *            the expect HTTP Response status as an object
	 * @param actualResposne
	 *            the response object holding the observed (actual) integer
	 *            status code
	 */
	public static void assertStatusEquals(final Status expectedStatus,
			final Response actualResponse) {
		Assert.assertEquals(expectedStatus.getStatusCode(),
				actualResponse.getStatus());
	}

}
