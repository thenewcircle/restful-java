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
  public Map<String,String> getFileExtensionMap() {
	  String text = (String)System.getProperties().get("vsp.test.fileExtensionFilters");
	  //  .txt:text/plain,.xml:application/xml,.json:application/json
	  // split ","
	  // split ":"
	  // add [0] to key, [1] value
	  
	  Map<String,String> map = new HashMap<>();
    map.put(".txt",  "text/plain");
    map.put(".xml",  "application/xml");
    map.put(".json", "application/json");
    return map;
  }
}
