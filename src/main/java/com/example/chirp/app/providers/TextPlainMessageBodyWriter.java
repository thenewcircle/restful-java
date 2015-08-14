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

import org.springframework.stereotype.Component;

import com.example.chirp.app.pub.PubUser;

@Component
@Provider
@Produces(MediaType.TEXT_PLAIN)
public class TextPlainMessageBodyWriter implements MessageBodyWriter<PubUser> {

	@Override
	public boolean isWriteable(Class type, Type genericType, 
			Annotation[] annotations, MediaType mediaType) {

//		if (PubUser.class.equals(type) == false) {
//			return false;
//			
//		} else if (MediaType.TEXT_PLAIN_TYPE.equals(mediaType) == false) {
//			return false;
//		}
		
		return true;
	}

	@Override
	public void writeTo(PubUser t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException {

		String realName = t.getRealName();
		entityStream.write(realName.getBytes());
	}

	@Override
	public long getSize(PubUser t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}
}
