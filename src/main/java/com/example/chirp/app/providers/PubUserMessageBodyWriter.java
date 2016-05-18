package com.example.chirp.app.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.example.chirp.app.pub.PubUser;

@Provider
@Produces(MediaType.TEXT_PLAIN)
public class PubUserMessageBodyWriter implements MessageBodyWriter<PubUser> {

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return true;
  }

  @Override
  public long getSize(PubUser t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return -1;
  }

  @Override
  public void writeTo(PubUser user, Class<?> type, Type genericType, Annotation[] annotations, 
                      MediaType mediaType, 
                      MultivaluedMap<String, Object> httpHeaders, 
                      OutputStream entityStream) throws IOException, WebApplicationException {

    String content = user.getRealName();
    entityStream.write(content.getBytes());
  }
}