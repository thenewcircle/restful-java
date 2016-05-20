package com.example.chirp.app.pub;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

public class MockUriInfo implements UriInfo {
  
  @Override public UriBuilder getBaseUriBuilder() {
    return new JerseyUriBuilder().scheme("http").host("mock.com");
  }
  
  @Override public String getPath() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getPath(boolean decode) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<PathSegment> getPathSegments() {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<PathSegment> getPathSegments(boolean decode) {
    throw new UnsupportedOperationException();
  }

  @Override
  public URI getRequestUri() {
    throw new UnsupportedOperationException();
  }

  @Override
  public UriBuilder getRequestUriBuilder() {
    throw new UnsupportedOperationException();
  }

  @Override
  public URI getAbsolutePath() {
    throw new UnsupportedOperationException();
  }

  @Override
  public UriBuilder getAbsolutePathBuilder() {
    throw new UnsupportedOperationException();
  }

  @Override
  public URI getBaseUri() {
    throw new UnsupportedOperationException();
  }

  @Override
  public MultivaluedMap<String, String> getPathParameters() {
    throw new UnsupportedOperationException();
  }

  @Override
  public MultivaluedMap<String, String> getPathParameters(boolean decode) {
    throw new UnsupportedOperationException();
  }

  @Override
  public MultivaluedMap<String, String> getQueryParameters() {
    throw new UnsupportedOperationException();
  }

  @Override
  public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<String> getMatchedURIs() {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<String> getMatchedURIs(boolean decode) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Object> getMatchedResources() {
    throw new UnsupportedOperationException();
  }

  @Override
  public URI resolve(URI uri) {
    throw new UnsupportedOperationException();
  }

  @Override
  public URI relativize(URI uri) {
    throw new UnsupportedOperationException();
  }
}