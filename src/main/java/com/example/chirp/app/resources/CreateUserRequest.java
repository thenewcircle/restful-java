package com.example.chirp.app.resources;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class CreateUserRequest {

  private @PathParam("username") String username; 
  private @FormParam("realName") String fullName;
  private @Context UriInfo uriInfo;

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  public UriInfo getUriInfo() {
    return uriInfo;
  }
  public void setUriInfo(UriInfo uriInfo) {
    this.uriInfo = uriInfo;
  }
  
  public void validate() {
    if (getUsername().contains(" ")) {
      throw new BadRequestException("The user name cannot contain spaces.");
    }
  }
}
