package com.example.chirp.app.pub;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class PubChirp {

    private final Map<String, URI> links = new LinkedHashMap<>();
    private final String id;
    private final String content;

    public PubChirp(@JsonProperty("links") Map<String, URI> links,
                    @JsonProperty("id") String id,
                    @JsonProperty("content") String content) {

        this.links.putAll(links);
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Map<String, URI> getLinks() {
        return links;
    }
}