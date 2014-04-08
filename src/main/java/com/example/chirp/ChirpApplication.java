package com.example.chirp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.jackson.JacksonFeature;

import com.example.chirp.providers.CsrfPreventionRequestFilter;
import com.example.chirp.providers.DuplicateEntityExceptionMapper;
import com.example.chirp.providers.FileExtensionRequestFilter;
import com.example.chirp.providers.NoSuchEntityExceptionMapper;
import com.example.chirp.resources.HelloResource;
import com.example.chirp.resources.PostResource;
import com.example.chirp.resources.UserResource;

@ApplicationPath("/rest")
public class ChirpApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> result = new HashSet<>();
		result.add(HelloResource.class);
		result.add(UserResource.class);
		result.add(PostResource.class);
		result.add(JacksonFeature.class);
		result.add(NoSuchEntityExceptionMapper.class);
		result.add(DuplicateEntityExceptionMapper.class);
		result.add(FileExtensionRequestFilter.class);
		result.add(CsrfPreventionRequestFilter.class);
		return result;
	}

}
