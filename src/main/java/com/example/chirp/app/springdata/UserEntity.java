package com.example.chirp.app.springdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;

@Entity
public class UserEntity {

  @Id
  private String username;
  private String realName;

  @OneToMany(targetEntity=ChirpEntity.class, mappedBy="user", fetch= FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private final List<ChirpEntity> chirps = new ArrayList<>();

  private UserEntity() {
  }

  public UserEntity(String username, String fullName) {
    this(username, fullName, Collections.<ChirpEntity>emptyList());
  }

  public UserEntity(String username, String realName, List<ChirpEntity> chirps) {
    this.username = username;
    this.realName = realName;

    if (chirps != null) {
      this.chirps.addAll(chirps);
    }
  }

  public UserEntity(User user) {
    this.username = user.getUsername();
    this.realName = user.getRealname();

    for (Chirp chirp : user.getChirps()) {
      ChirpEntity chirpEntity = new ChirpEntity(this, chirp);
      this.chirps.add(chirpEntity);
    }
  }

  public String getUsername() {
    return username;
  }

  public String getRealName() {
    return realName;
  }

  public List<ChirpEntity> getChirps() {
    return chirps;
  }

  public User toUser() {
    User user = new User(username, realName);
    for (ChirpEntity chirp : this.chirps) {
      user.addChirp(chirp.toChirp(user));
    }
    return user;
  }
}
