package com.example.chirp.app.pub;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PubChirps {
  private final int limit;
  private final int offset;
  private final int total;
  private final int count;
  private final List<PubChirp> items;
  private final Map<String, URI> _links = new LinkedHashMap<>();

  public PubChirps(@JsonProperty("_links") Map<String, URI> _links, 
                   @JsonProperty("chirps") List<PubChirp> chirps,
                   @JsonProperty("limit") int limit, 
                   @JsonProperty("offset") int offset, 
                   @JsonProperty("total") int total,
                   @JsonProperty("count") int count) {

    this.items = chirps;
    this.limit = limit;
    this.offset = offset;
    this.total = total;
    this.count = count;
    this._links.putAll(_links);
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