package com.example.chirp.app.pub;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PubChirps {

    private final int limit;
    private final int offset;
    private final int total;
    private final int count;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<PubChirp> items;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<URI> itemLinks = new ArrayList<>();

    private final Map<String, URI> _links = new LinkedHashMap<>();

    public PubChirps(@JsonProperty("links") Map<String, URI> _links,
                     @JsonProperty("chirps") List<PubChirp> chirps,
                     @JsonProperty("itemLinks") List<URI> itemLinks,
                     @JsonProperty("limit") int limit,
                     @JsonProperty("offset") int offset,
                     @JsonProperty("total") int total,
                     @JsonProperty("count") int count) {

        this.items = chirps;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
        this.count = count;

        if (itemLinks != null) {
            this.itemLinks.addAll(itemLinks);
        }

        if (_links != null) {
            this._links.putAll(_links);
        }
    }

    public List<URI> getItemLinks() {
        return itemLinks;
    }

    public List<PubChirp> getItems() {
        return items;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public Map<String, URI> get_links() {
        return _links;
    }
}