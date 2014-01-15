package chirp.service.resources;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.ParameterizedType;
import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;

/**
 * Use this class to provide common implementations of entity client operations assuming
 * a fixed set of data.
 * 
 * @author Gordon Force
 *
 * @param <R> the resource class under test
 * @param <E> the entity class under test
 */
public abstract class AbstractEntityClientImpl<R, E> implements EntityClient<E> {

	@SuppressWarnings("unchecked")
	private final Class<R> resourceClass = (Class<R>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];

	@SuppressWarnings("unchecked")
	private final Class<E> entityClass = (Class<E>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[1];

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

		final Response response = jt.target(location.getPath()).request()
				.post(Entity.form(postForm));
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
		return getWithStatus(response.getLocation(), mediaType, Status.OK);
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
	public static void assertStatusEquals(final Status expectedStatus,
			final Response actualResponse) {
		Assert.assertEquals(expectedStatus.getStatusCode(),
				actualResponse.getStatus());
	}

}
