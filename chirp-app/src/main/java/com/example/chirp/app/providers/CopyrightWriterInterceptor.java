package com.example.chirp.app.providers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

@Provider
public class CopyrightWriterInterceptor implements WriterInterceptor {

	public static final String XML_COPYRIGHT = "<!-- Copyright Mine -->";
	public static final String JSON_COPYRIGHT = ",\"copyright\":\"Copyright Mine\"";
	
	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		MediaType mediaType = context.getMediaType();
		if (MediaType.APPLICATION_XML_TYPE.isCompatible(mediaType)) {
			addXmlCopyright(context);
		} else if (MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType)) {
			addJsonCopyright(context);
		} else {
			context.proceed();
		}
	}

	private void addXmlCopyright(WriterInterceptorContext context) throws WebApplicationException, IOException {
		OutputStream oos = context.getOutputStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		context.setOutputStream(baos);
		context.proceed();
		baos.flush();
		
		byte[] bytes = baos.toByteArray();
		String content = new String(bytes);

		int pos = content.lastIndexOf(">");
		if (pos >= 0) {
			pos = content.lastIndexOf("</", pos);
			if (pos >= 0) {
				String left = content.substring(0, pos);
				String right = content.substring(pos);
				content = left + XML_COPYRIGHT + right;
			}
		}
		
		oos.write(content.getBytes());
	}

	private void addJsonCopyright(WriterInterceptorContext context) throws WebApplicationException, IOException {
		OutputStream oos = context.getOutputStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		context.setOutputStream(baos);
		context.proceed();
		baos.flush();
		
		byte[] bytes = baos.toByteArray();
		String content = new String(bytes);

		int pos = content.lastIndexOf("}");
		if (pos >= 0) {
			String left = content.substring(0, pos);
			String right = content.substring(pos);
			content = left + JSON_COPYRIGHT + right;
		}
		
		oos.write(content.getBytes());
	}
}
