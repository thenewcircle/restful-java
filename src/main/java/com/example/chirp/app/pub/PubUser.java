package com.example.chirp.app.pub;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class PubUser {

    private final Map<String, URI> links = new LinkedHashMap<>();
    private final String username;
    private final String realName;

    public PubUser(@JsonProperty("links") Map<String, URI> links,
                   @JsonProperty("username") String username,
                   @JsonProperty("realName") String realName) {

        this.username = username;
        this.realName = realName;
        this.links.putAll(links);
    }

    public String getUsername() {
        return username;
    }

    public String getRealName() {
        return realName;
    }

    public Map<String, URI> getLinks() {
        return links;
    }
}