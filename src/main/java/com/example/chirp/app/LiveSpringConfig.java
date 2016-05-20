package com.example.chirp.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("live")
@Configuration
public class LiveSpringConfig {

  @Bean(name="fileExtensionMap")
  public Map<String,String> createFileExtensionMap() {
    
    Map<String,String> map = new HashMap<>();
    map.put(".txt",  "text/plain");
    map.put(".xml",  "application/xml");
    map.put(".json", "application/json");
 
    return map;
  }
}






