package com.example.chirp.app.stores;

import java.util.Deque;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;

public interface UserStore {

	void clear();

	User createUser(String username, String fullName);

	User getUser(String username);

	Deque<User> getUsers();

	void updateUser(User vader);

	Chirp getChirp(String chirpId);
	
}
