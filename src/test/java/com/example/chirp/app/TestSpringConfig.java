package com.example.chirp.app;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
@Configuration
public class TestSpringConfig {

	private final Logger log = LoggerFactory.getLogger(getClass());

  public TestSpringConfig() {
	  log.warn("Created.");
  }
	
  @Bean(name="fileExtensionMap")
  public Map<String,String> getFileExtensionMap() {
    Map<String,String> map = new HashMap<>();
    map.put(".txt",  "text/plain");
    map.put(".xml",  "application/xml");
    map.put(".json", "application/json");
    map.put(".avi",  "application/avi");
    return map;
  }
}