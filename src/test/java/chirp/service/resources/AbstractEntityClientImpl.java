package chirp.service.resources;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;

import chirp.service.representations.EntityRepresentation;

/**
 * Use this class to provide common implementations of entity client operations assuming
 * a fixed set of data.
 * 
 * @author Gordon Force
 *
 * @param <R> the resource class under test
 * @param <E> the entity class under test
 * @param <C> the collections entity resource under test
 */
public abstract class AbstractEntityClientImpl<R, E extends EntityRepresentation, C extends EntityRepresentation> implements EntityClient<E,C> {
	
	private Type getTypeClass(final int index) {
		return ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[index];
	}

	@SuppressWarnings("unchecked")
	private final Class<R> resourceClass = (Class<R>) getTypeClass(0);

	@SuppressWarnings("unchecked")
	private final Class<E> entityClass = (Class<E>) getTypeClass(1);

	@SuppressWarnings("unchecked")
	private final Class<C> entityCollectionClass = (Class<C>) getTypeClass(2);

	private final JerseyTest jt;

	public AbstractEntityClientImpl(JerseyTest jt) {
		this.jt = jt;
	}
	
	public JerseyTest getJerseyTest() {
		return jt;
	}

	protected Response createWithStatusInternal(final Form postForm,
			final Status expectedStatus) {

		return createWithStatusInternal(postForm,
				UriBuilder.fromResource(resourceClass).build(), expectedStatus);

	}

	protected Response createWithStatusInternal(final Form postForm,
			URI location, final Status expectedStatus) {
		
		// http://localhost:8080/users,  
		// grizzly and jersey imemory servers only deal with paths, or what is to the right of the hostname.

		final Response response = jt.target(location.getPath())   // note: target method only accepts paths that are strings.
				.request().post(Entity.form(postForm));
		assertStatusEquals(expectedStatus, response);
		return response;
	}

	@Override
	public E createWithGetLocationVerify(MediaType mediaType) {
		Response response = createWithStatus(Status.CREATED);
		return get(response.getLocation(), mediaType);
	}

	@Override
	public Response createWithHeadLocationVerify(MediaType mediaType) {
		Response response = createWithStatus(Status.CREATED);
		Response getResponse = getWithStatus(response.getLocation(), mediaType, Status.OK);
		return getResponse;
	}

	@Override
	public Response getWithStatus(final URI location, MediaType mediaType,
			final Status expectedStatus) {
		final Response response = jt.target(location.getPath())
				.request(mediaType).get();
		assertStatusEquals(expectedStatus, response);
		return response;
	}

	@Override
	public E get(final URI location, MediaType mediaType) {
		final E entity = jt.target(location.getPath()).request(mediaType)
				.get(entityClass);
		assertNotNull(entity);
		return entity;
	}
	
	
	protected abstract String createEntityCollectionURIPath();
	
	@Override
	public C getAll(final MediaType mediaType) {
		final String uriPath = createEntityCollectionURIPath();
		final Response response = getJerseyTest()
				.target(uriPath).request(mediaType).get();
		
		assertStatusEquals(Status.OK,response);
		assertTrue(response.hasEntity());
		
		// @SuppressWarnings("unchecked")
		final C collection = (C)response.readEntity(entityCollectionClass);
		assertNotNull(collection);
		assertNotNull(collection.getSelf());
		assertEquals(uriPath,collection.getSelf().toString());
		
		return collection; 
	}


	public Class<R> getResourceClass() {
		return resourceClass;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public Class<C> getEntityCollectionClass() {
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
