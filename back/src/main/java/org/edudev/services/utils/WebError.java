package org.edudev.services.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

public class WebError {

	public static void sendError(Status status, String message) {
		ResponseBuilderImpl builder = new ResponseBuilderImpl();
		builder.status(status);
		builder.entity(message);
		Response response = builder.build();
		throw new WebApplicationException(response);
	}
}
