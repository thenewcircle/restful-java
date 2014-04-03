package com.example.chirp.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.example.chirp.model.DuplicateEntityException;

@XmlRootElement(name="ServerError")
class ErrorData {
	@XmlElement
	public String msg;
	@XmlElement
	public int code;
	
	public String toString() {
		return msg;
	}
}

@Provider
public class DuplicateEntityExceptionMapper implements ExceptionMapper<DuplicateEntityException> {

	@Override
	public Response toResponse(DuplicateEntityException exception) {
		String body = exception.getMessage();
		ErrorData data = new ErrorData();
		data.msg = exception.getMessage();
		data.code = 5;
		Response response = Response.status(Status.CONFLICT).entity(data).build();
		return response;
	}

}
