package com.example.chirp.app.pub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PubUserTest {

    @Test
    public void testJsonTranslation() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, URI> links = new LinkedHashMap<>();
        links.put("self", URI.create("example.com/mikey/was/here"));

        PubUser pubUser = new PubUser(links, "donald", "Donald Duck");

        String json = objectMapper.writeValueAsString(pubUser);
        PubUser newUser = objectMapper.readValue(json, PubUser.class);

    }

    @Test
    public void testXmlTranslation() throws Exception {

        XmlMapper objectMapper = new XmlMapper();

        Map<String, URI> links = new LinkedHashMap<>();
        links.put("self", URI.create("example.com/mikey/was/here"));

        PubUser pubUser = new PubUser(links, "donald", "Donald Duck");

        String json = objectMapper.writeValueAsString(pubUser);
        PubUser newUser = objectMapper.readValue(json, PubUser.class);

    }
}