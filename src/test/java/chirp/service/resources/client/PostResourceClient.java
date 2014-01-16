package chirp.service.resources.client;

import java.net.URI;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;

import chirp.service.representations.PostCollectionRepresentation;
import chirp.service.representations.PostRepresentation;
import chirp.service.representations.UserRepresentation;
import chirp.service.resources.PostResource;
import chirp.service.resources.ResourceTestClientFactory;
import chirp.service.resources.UserResource;

public class PostResourceClient
		extends
		AbstractEntityClientImpl<PostResource, PostRepresentation, PostCollectionRepresentation> {

	private int count = 1;
	private UserRepresentation user;
	private final JerseyTest jt;

	public PostResourceClient(JerseyTest jt) {
		super(jt);
		this.jt = jt;
	}

	private UserRepresentation getUser() {
		if (this.user == null)
			this.user = (UserRepresentation) ResourceTestClientFactory.Default
					.getInstance()
					.create(UserResource.class, jt)
					.createWithGetLocationVerify(
							MediaType.APPLICATION_JSON_TYPE);

		return this.user;
	}

	private URI postBase() {
		return UriBuilder.fromPath("/posts").path(getUser().getUsername())
				.build();
	}

	@Override
	public Response createWithStatus(Status expectedStatus) {

		final Form form = new Form().param("content",
				String.format("Posting message # %d", count++));
		final Response response = createWithStatusInternal(form, postBase(),
				expectedStatus);

		return response;
	}

	@Override
	protected String createEntityCollectionURIPath() {
		return postBase().getPath();
	}

}
