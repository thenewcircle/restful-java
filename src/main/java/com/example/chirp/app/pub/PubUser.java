package com.example.chirp.app.pub;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class PubUser {

    private final Map<String, URI> links = new LinkedHashMap<>();
    private final String username;
    private final String realName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final PubChirps chirps;

    public PubUser(@JsonProperty("links") Map<String, URI> links,
                   @JsonProperty("username") String username,
                   @JsonProperty("realName") String realName,
                   @JsonProperty("chirps") PubChirps chirps) {

        this.username = username;
        this.realName = realName;
        this.links.putAll(links);
        this.chirps = chirps;
    }

    public PubChirps getChirps() {
        return chirps;
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