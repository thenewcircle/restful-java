package com.example.chirp.app;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import com.example.chirp.pub.PubUser;

public class PubUserMessageBodyWriter implements MessageBodyWriter {

	@Override
	public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (MediaType.TEXT_PLAIN_TYPE.equals(mediaType) == false) {
			return false;
		}
		if (PubUser.class.equals(type) == false) {
			return false;
		}
		return true;
	}

	@Override
	public long getSize(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	  public void writeTo(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
	      PubUser user = (PubUser)t;
	      String realName = user.getRealName();
	      entityStream.write(realName.getBytes());
	}
}
