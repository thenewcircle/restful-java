package com.example.chirp.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.chirp.kernel.stores.UsersStore;

@Component
public class UsersStoreAccessor {

	@Autowired
	// @Qualifier("inMemoryUsersStore")
	@Qualifier("springDataUserStore")
	private UsersStore usersStore;

	public UsersStoreAccessor() {
	}
	
	public UsersStore getUsersStore() {
		return usersStore;
	}
	
}
