package com.example.chirp.app.springdata;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.ChirpId;
import com.example.chirp.app.kernel.User;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ChirpEntity {

  @Id
  private String id;
  private String content;

  @ManyToOne
  private UserEntity user;

  private ChirpEntity() {
  }

  public ChirpEntity(UserEntity user, String id, String content) {
    this.user = user;
    this.id = id;
    this.content = content;
  }

  public ChirpEntity(UserEntity user, Chirp chirp) {
    this.user = user;
    this.id = chirp.getId().toString();
    this.content = chirp.getContent();
  }

  public UserEntity getUser() {
    return user;
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public Chirp toChirp(User user) {
    ChirpId chirpId = new ChirpId(id);
    return new Chirp(chirpId, content, user);
  }
}
