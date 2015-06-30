package com.example.chirp.pub;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

public class PubUser {

  private final URI self;
  private final String username;
  private final String realName;

  public PubUser() {
	  self = null;
	  username = null;
	  realName = null;
  }
  
  public PubUser(URI self, String username, String realName) {
	super();
	this.self = self;
	this.username = username;
	this.realName = realName;
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
