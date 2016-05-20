package com.example.chirp.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.chirp.app.resources.ResourceTestSupport;
import com.example.chirp.app.stores.InMemoryUserStore;
import com.example.chirp.app.stores.UserStore;

@Profile("test")
@Configuration
public class TestSpringConfig {

  @Bean
  public UserStore whatever() {
    UserStore userStore = new InMemoryUserStore(true);
    ResourceTestSupport.userStore = userStore;
    return userStore;
  }
  
  @Bean(name="fileExtensionMap")
  public Map<String,String> createFileExtensionMap() {

    Map<String,String> map = new HashMap<>();
    
    map.put(".txt",  "text/plain");
    map.put(".xml",  "application/xml");
    map.put(".json", "application/json");
    map.put(".avi",  "application/avi");
    
    return map;
  }
}










