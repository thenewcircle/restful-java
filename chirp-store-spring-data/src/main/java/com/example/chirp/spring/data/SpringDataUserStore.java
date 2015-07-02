package com.example.chirp.spring.data;

import com.example.chirp.kernel.User;
import com.example.chirp.kernel.exceptions.NoSuchEntityException;
import com.example.chirp.kernel.stores.UsersStore;
import com.example.chirp.kernel.stores.UsersStoreUtils;
import com.example.chirp.spring.data.entities.UserEntity;
import com.example.chirp.spring.data.repository.UserRepository;

import javax.inject.Inject;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class SpringDataUserStore implements UsersStore {

  private UserRepository userRepository;

  @Inject
  public SpringDataUserStore(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void setSeedDatabase(boolean seedDatabase) {
    if (seedDatabase) {
      UsersStoreUtils.resetAndSeedRepository(this);
    }
  }

  @Override
  public void clear() {
    userRepository.deleteAll();
  }

  @Override
  public User createUser(String username, String fullName) {
    UserEntity userEntity = new UserEntity(username, fullName);
    userRepository.save(userEntity);
    return userEntity.toUser();
  }

  @Override
  public void updateUser(User user) {
    UserEntity userEntity = new UserEntity(user);
    userRepository.save(userEntity);
    List<UserEntity> userEntities = userRepository.findByUsername(user.getUsername());
    System.out.print("");
  }

  @Override
  public User getUser(String username) {
    List<UserEntity> userEntities = userRepository.findByUsername(username);
    if (userEntities.isEmpty()) {
      throw new NoSuchEntityException();
    }
    return userEntities.get(0).toUser();
  }

  @Override
  public Deque<User> getUsers() {
    List<UserEntity> userEntities = userRepository.findAll();
    Deque<User> users = new LinkedList<>();
    for (UserEntity userEntity : userEntities) {
      users.add(userEntity.toUser());
    }
    return users;
  }
}