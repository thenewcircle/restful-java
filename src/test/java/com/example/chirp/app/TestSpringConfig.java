package com.example.chirp.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.chirp.app.stores.InMemoryUserStore;
import com.example.chirp.app.stores.UserStore;

@Profile("test")
@Configuration
@ComponentScan("com.example.chirp.app")
public class TestSpringConfig {

  @Bean(name="fileExtensionMap")
  public Map<String,String> getFileExtensionMap() {
	Map<String,String> map = new HashMap<>();
    map.put(".txt",  "text/plain");
    map.put(".xml",  "application/xml");
    map.put(".json", "application/json");
    map.put(".avi",  "application/avi");
    return map;
  }
 
  @Bean
  public UserStore getUserStore() {
    return new InMemoryUserStore(true);
  }
}