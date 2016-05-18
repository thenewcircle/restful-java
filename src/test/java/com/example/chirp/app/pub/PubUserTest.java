package com.example.chirp.app.pub;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class PubUserTest {
  @Test public void testJsonTranslation() throws Exception {
    
    ObjectMapper mapper = new ObjectMapper();
    
    Map<String,URI> links = new LinkedHashMap<>();
    links.put("self", URI.create("http://whatever/a"));
    links.put("chirps", URI.create("http://whatever/b"));
    
    PubUser oldUser = new PubUser(links, "mickey", "Mickey Mouse");
    String json = mapper.writeValueAsString(oldUser);
    PubUser newUser = mapper.readValue(json, PubUser.class);
    
    Assert.assertEquals(oldUser.getUsername(), newUser.getUsername());
    Assert.assertEquals(oldUser.getRealName(), newUser.getRealName());
    Assert.assertEquals(oldUser.getLinks(), newUser.getLinks());
  }

  @Test public void testXmlTranslation() throws Exception {
    XmlMapper mapper = new XmlMapper();
    
    Map<String,URI> links = new LinkedHashMap<>();
    links.put("self", URI.create("http://whatever/a"));
    links.put("chirps", URI.create("http://whatever/b"));
    
    PubUser oldUser = new PubUser(links, "mickey", "Mickey Mouse");
    String json = mapper.writeValueAsString(oldUser);
    PubUser newUser = mapper.readValue(json, PubUser.class);
    
    Assert.assertEquals(oldUser.getUsername(), newUser.getUsername());
    Assert.assertEquals(oldUser.getRealName(), newUser.getRealName());
    Assert.assertEquals(oldUser.getLinks(), newUser.getLinks());
  }
}



