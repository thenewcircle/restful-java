package com.example.chirp.dist.grizzly;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.chirp.spring.data.SpringDataUserStore;
import com.example.chirp.spring.data.repository.UserRepository;
import com.example.chirp.store.memory.InMemoryUsersStore;

@Configuration
public class GrizzlySpringConfig {

	public GrizzlySpringConfig() {
		System.out.println("I'm in the spring config");
	}
	
	@Bean(name="usersStore")
	public SpringDataUserStore getSpringDataUserStore(UserRepository userRepository) {
		// return new InMemoryUsersStore(true);

		SpringDataUserStore userStore = new SpringDataUserStore(userRepository);
		userStore.setSeedDatabase(true);
		return userStore;
	}
}
