package com.example.chirp.services;

import java.util.Collection;

import com.example.chirp.model.Post;
import com.example.chirp.model.User;

public interface ChirpRepository {

	/**
	 * Use this method to empty the current UserRepository.
	 */
	public void clear();

	public User createUser(String username, String realname);

	public boolean isExistingUser(String username);

	public Collection<User> getUsers();

	public User getUser(String username);

	public void deleteUser(String username);

	public Post createPost(String username, String content);

	public Post createPost(String username, String content, String guid);

	public boolean isExistingPost(String key);

	public Collection<Post> getAllPosts();

	public Collection<Post> getPostsForUser(String username);

	public Post getPost(String id);

	public void deletePost(String id);

	public int createBulkDeletion();

	public void addToBulkDeletion(int id, String username);

	public void cancelBulkDeletion(int id);

	public boolean commitBulkDeletion(int id);

}
