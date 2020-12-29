package org.edudev.resources;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.edudev.models.Notification;
import org.edudev.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class NotificationResource {

	@Inject
	private NotificationService service;

	@Context
	UriInfo uriInfo;

	@POST
	public Response save(Notification notification) {
		service.save(notification);
		
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", notification.getId()).build();
		return Response.created(uri).entity(notification).build();
	}
	
	@GET
	public Response findAllPaged(
			@QueryParam("min") Integer min, 
			@QueryParam("max") Integer max) {
		
		Page<Notification> notifications = service.findAllByPaged(PageRequest.of(min, max, Sort.Direction.ASC, "title"));
		return Response.ok(notifications).build();
	}
}
