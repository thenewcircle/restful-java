package com.example.chirp.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

//ignore anything the client sends that I don't know about
@JsonIgnoreProperties(ignoreUnknown=true) 
public class PubChirps {

	private final URI self;
	private final URI prev;
	private final URI next;

	private final int size;
	private final int offset;
	
	@JsonProperty("items") // called items in JSON
	@JacksonXmlProperty(localName="chirp")
	@JacksonXmlElementWrapper(localName="xml-items") // called xml-items in XML
	private final List<PubChirp> chirps = new ArrayList<>();

	@JsonCreator
	public PubChirps(@JsonProperty("selfLink") URI self,
					 @JsonProperty("prev") URI prev,
					 @JsonProperty("next") URI next,
					 @JsonProperty(value="offset", defaultValue="0") int offset,
					 @JsonProperty(value="size", defaultValue="4") int size,
					 
					 // This will work for JSON, but not xml (does not match xml-items)
			         @JsonProperty("items") Collection<PubChirp> chirps) {

		this.self = self;
		this.prev = prev;
		this.next = next;
		
		this.size = size;
		this.offset = offset;
		
		if (chirps != null) {
			this.chirps.addAll(chirps);
		}
	}

	public PubChirps(URI baseUri, int offset, int size, Collection<PubChirp> chirps) {

		if (offset < 0) offset = 0;
		if (size == 0) size = 4;
		
		String basepath = baseUri.toString();
		
		this.self = URI.create(basepath + "?offset="+offset+"&size="+size);
		this.prev = (offset-size < 0) ? null : URI.create(basepath + "?offset="+(offset-size)+"&size="+size);
		this.next = URI.create(basepath + "?offset="+(offset+size)+"&size="+size);
		
		this.size = size;
		this.offset = offset;
		
		if (chirps == null) return;
		List<PubChirp> list = new ArrayList<>(chirps);

		int max = Math.min(offset+size, chirps.size());
		for (int i = offset; i < max; i++) {
			PubChirp chirp = list.get(i);
			this.chirps.add(chirp);
		}
	}
	
	@JsonProperty("selfLink")
	public URI getSelf() {
		return self;
	}

	public URI getPrev() {
		return prev;
	}

	public URI getNext() {
		return next;
	}

	public int getSize() {
		return size;
	}

	public int getOffset() {
		return offset;
	}

	public List<PubChirp> getChirps() {
		return chirps;
	}

	@JsonIgnore // do not send to the client
	public java.util.Date getCreatedAt() {
		return new java.util.Date();
	}
}
