package com.example.chirp.app;

import com.example.chirp.store.memory.InMemoryUsersStore;

public class MemoryStoreUtil {

	public static final InMemoryUsersStore usersStore = new InMemoryUsersStore(true);
	
}
