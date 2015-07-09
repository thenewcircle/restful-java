package com.example.chirp.dist.resteasy;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.providers.AuthenticationFilter;
import com.example.chirp.app.providers.FileExtensionRequestFilter;

import javax.ws.rs.ApplicationPath;

import org.springframework.web.context.support.WebApplicationContextUtils;

@ApplicationPath("/")
public class ResteasyChirpApplication extends ChirpApplication {

	public ResteasyChirpApplication() {
		// RESTEasy is unique in that any resource or provider
		// that comes from Spring must not be registered here.
		super.classes.remove(AuthenticationFilter.class);
		super.classes.remove(FileExtensionRequestFilter.class);
	}
}
