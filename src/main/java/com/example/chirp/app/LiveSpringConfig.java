package com.example.chirp.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.chirp.app.springdata.SpringDataUserStore;
import com.example.chirp.app.springdata.UserRepository;
import com.example.chirp.app.stores.UserStore;
import com.example.chirp.app.stores.UserStoreUtils;

@Profile("live")
@Configuration
public class LiveSpringConfig {

  @Bean
  public UserStore whatever(UserRepository userRepository) {
    UserStore userStore = new SpringDataUserStore(userRepository);
    //UserStoreUtils.resetAndSeedRepository(userStore);
    return userStore;
    // return new InMemoryUserStore(true);
  }
  
  @Bean(name="fileExtensionMap")
  public Map<String,String> createFileExtensionMap() {
    
    Map<String,String> map = new HashMap<>();
    map.put(".txt",  "text/plain");
    map.put(".xml",  "application/xml");
    map.put(".json", "application/json");
 
    return map;
  }
}






