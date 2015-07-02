package com.example.chirp.dist.resteasy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.chirp.store.memory.InMemoryUsersStore;

@Configuration
public class ResteasySpringConfig {

	public ResteasySpringConfig() {
		System.out.println("I'm in the spring config");
	}
	
	@Bean(name="inMemoryUsersStore")
	public InMemoryUsersStore getInMemoryUsersStore() {
		return new InMemoryUsersStore(true);
	}
	
}
