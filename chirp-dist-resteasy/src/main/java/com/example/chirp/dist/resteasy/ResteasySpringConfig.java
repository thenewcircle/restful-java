package com.example.chirp.dist.resteasy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.chirp.spring.data.SpringDataUserStore;
import com.example.chirp.spring.data.repository.UserRepository;
import com.example.chirp.store.memory.InMemoryUsersStore;

@Configuration
public class ResteasySpringConfig {

	@Bean(name="usersStore")
	public SpringDataUserStore getSpringDataUserStore(UserRepository userRepository) {
		// return new InMemoryUsersStore(true);

		SpringDataUserStore userStore = new SpringDataUserStore(userRepository);
		userStore.setSeedDatabase(true);
		return userStore;
	}
}
