package com.example.chirp.app.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.chirp.app.providers.AuthenticationFilter;
import com.example.chirp.app.providers.FileExtensionRequestFilter;
import com.example.chirp.kernel.stores.UsersStore;
import com.example.chirp.store.memory.InMemoryUsersStore;

@Configuration
public class ChirpAppTestSpringConfig {

	public ChirpAppTestSpringConfig() {
	}
	
	@Bean(name="usersStore")
	public UsersStore getUserStore() {
		return new InMemoryUsersStore(true);
	}
}
