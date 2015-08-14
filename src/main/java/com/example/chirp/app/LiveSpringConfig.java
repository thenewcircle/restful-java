package com.example.chirp.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.chirp.app.springdata.UserRepository;
import com.example.chirp.app.stores.InMemoryUserStore;
import com.example.chirp.app.stores.UserStore;

@Profile("live")
@Configuration
@ComponentScan("com.example.chirp.app")
public class LiveSpringConfig {
  
  @Bean(name="fileExtensionMap")
  public Map<String,String> getFileExtensionMap() {
	Map<String,String> map = new HashMap<>();
    map.put(".txt",  "text/plain");
    map.put(".xml",  "application/xml");
    map.put(".json", "application/json");
    return map;
  }

   @Bean
   public UserStore getWhatever(UserRepository userRepository) {
	   UserStore userStore = new InMemoryUserStore(true);
//     UserStore userStore = new SpringDataUserStore(userRepository);
//     UserStoreUtils.resetAndSeedRepository(userStore);
     return userStore;
   }
}