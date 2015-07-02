package com.example.chirp.spring.data.repository;

import com.example.chirp.spring.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("select u from UserEntity u where u.username = ?1")
  List<UserEntity> findByUsername(String username);

}