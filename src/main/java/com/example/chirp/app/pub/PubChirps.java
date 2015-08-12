package com.example.chirp.app.pub;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PubChirps {

	private final List<PubChirp> chirps;
	
	private final int limit;
	private final int offset;
	private final int total;
	private final int count;
	
	private final URI self;
	private final URI userLink;

	private final URI first;
	private final URI prev;
	private final URI next;
	private final URI last;
	
	public PubChirps(@JsonProperty("chirps") List<PubChirp> chirps, 
					 @JsonProperty("limit") int limit, 
					 @JsonProperty("offset") int offset, 
	 				 @JsonProperty("total") int total, 
	 				 @JsonProperty("count") int count, 
  					 @JsonProperty("self") URI self, 
  					 @JsonProperty("userLink") URI userLink,
   					 @JsonProperty("first") URI first,
   					 @JsonProperty("prev") URI prev,
   					 @JsonProperty("next") URI next,
   					 @JsonProperty("last") URI last) {
		
		this.chirps = chirps;
		this.limit = limit;
		this.offset = offset;
		this.total = total;
		this.count = count;
		
		this.self = self;
		this.userLink = userLink;

		this.first = first;
		this.prev = prev;
		this.next = next;
		this.last = last;
	}
	
	public URI getFirst() {
		return first;
	}

	public URI getPrev() {
		return prev;
	}

	public URI getNext() {
		return next;
	}

	public URI getLast() {
		return last;
	}

	public List<PubChirp> getChirps() {
		return chirps;
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
	public URI getSelf() {
		return self;
	}
	public URI getUserLink() {
		return userLink;
	}
}
