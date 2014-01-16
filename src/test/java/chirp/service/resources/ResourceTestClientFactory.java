package chirp.service.resources;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.test.JerseyTest;

import chirp.service.representations.Representation;
import chirp.service.resources.PostResource;
import chirp.service.resources.UserResource;
import chirp.service.resources.client.PostResourceClient;
import chirp.service.resources.client.UserResourceClient;

public interface ResourceTestClientFactory {

	static public class Default implements ResourceTestClientFactory {
		private static ResourceTestClientFactory instance = new Default();

		public static ResourceTestClientFactory getInstance() {
			return instance;
		}

		private Map<Class<?>, Class<? extends ResourceTestClient<? extends Representation, ? extends Representation>>> clients = new HashMap<>();

		private Default() {
			clients.put(UserResource.class, UserResourceClient.class);
			clients.put(PostResource.class, PostResourceClient.class);
		}

		public ResourceTestClient<? extends Representation, ? extends Representation> create(
				Class<?> resourceClass, JerseyTest jerseyTest) {

			if (resourceClass == null)
				throw new NullPointerException(
						"resouce parameter cant not be null");

			try {
				return clients.get(resourceClass)
						.getConstructor(JerseyTest.class)
						.newInstance(jerseyTest);
			} catch (NullPointerException npe) {
				throw new IllegalArgumentException(
						"An entity client does not exist for the resource class "
								+ resourceClass);
			}
			catch (Exception e) {
				throw new RuntimeException(
						"Could not create entity client for " + resourceClass,
						e);
			}
		}
	};

	ResourceTestClient<? extends Representation, ? extends Representation> create(
			Class<?> resourceClass, JerseyTest jerseyTest);
}
