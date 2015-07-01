package com.example.chirp.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

//ignore anything the client sends that I don't know about
@JsonIgnoreProperties(ignoreUnknown=true) 
public class PubChirps {

	private final URI self;
	
	@JsonProperty("items") // called items in JSON
	@JacksonXmlProperty(localName="chirp")
	@JacksonXmlElementWrapper(localName="xml-items") // called xml-items in XML
	private final List<PubChirp> chirps = new ArrayList<>();

	@JsonCreator
	public PubChirps(@JsonProperty("selfLink") URI self, 
			         // This will work for JSON, but not xml (does not match xml-items)
			         @JsonProperty("items") Collection<PubChirp> chirps) {

		this.self = self;

		if (chirps != null) {
			this.chirps.addAll(chirps);
		}
	}

	@JsonProperty("selfLink")
	public URI getSelf() {
		return self;
	}

	public List<PubChirp> getChirps() {
		return chirps;
	}

	@JsonIgnore // do not send to the client
	public java.util.Date getCreatedAt() {
		return new java.util.Date();
	}
}
