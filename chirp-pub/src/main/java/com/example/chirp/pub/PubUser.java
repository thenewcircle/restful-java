package com.example.chirp.pub;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PubUser {

  private final URI self;
  private final String username;
  private final String realName;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final List<URI> chirpLinks = new ArrayList<>();
  
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final List<PubChirp> chirps = new ArrayList<>();

  @JsonCreator
  public PubUser(@JsonProperty("self") URI self, 
		  		 @JsonProperty("username") String username, 
		  		 @JsonProperty("realName") String realName) {
	super();
	this.self = self;
	this.username = username;
	this.realName = realName;
  }
  
  public PubUser(URI self, String username, String realName, PubChirp... chirps) {
	this(self, username, realName);
	
	if (chirps != null) {
		Collections.addAll(this.chirps, chirps);
	}
  }
  
  public PubUser(URI self, String username, String realName, URI... chirpLinks) {
	this(self, username, realName);
	
	if (chirpLinks != null) {
		Collections.addAll(this.chirpLinks, chirpLinks);
	}
  }

  public List<URI> getChirpLinks() {
	return chirpLinks;
  }

  public List<PubChirp> getChirps() {
	return chirps;
  }

	public URI getSelf() {
		return self;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getRealName() {
		return realName;
	}
	
	  
	}
