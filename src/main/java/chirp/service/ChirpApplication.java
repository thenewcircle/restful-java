package chirp.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.jackson.JacksonFeature;

import chirp.model.UserRepository;
import chirp.service.resources.DuplicateEntityExceptionMapper;
import chirp.service.resources.HelloResource;
import chirp.service.resources.NoSuchEntityExceptionMapper;
import chirp.service.resources.UserResource;

@ApplicationPath("/rest")
public class ChirpApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> result = new HashSet<>();
		result.add(JacksonFeature.class);
		result.add(HelloResource.class);
		result.add(UserResource.class);
		result.add(NoSuchEntityExceptionMapper.class);
		result.add(DuplicateEntityExceptionMapper.class);
		return result;
	}

}
