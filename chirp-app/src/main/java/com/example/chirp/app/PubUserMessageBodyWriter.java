package com.example.chirp.app;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.example.chirp.pub.PubUser;

@Provider
public class PubUserMessageBodyWriter implements MessageBodyWriter<PubUser> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (MediaType.TEXT_PLAIN_TYPE.equals(mediaType) == false) {
			return false;
		}
		return PubUser.class.equals(type);
	}

	@Override
	public long getSize(PubUser t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void writeTo(PubUser user, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
					throws IOException, WebApplicationException {

	      String realName = user.getRealName();
	      entityStream.write(realName.getBytes());
	}
}
