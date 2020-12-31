package org.edudev.resources;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.edudev.models.dtos.ClientFriendDTO;
import org.edudev.services.ClientFriendService;
import org.springframework.http.MediaType;

@Path("/friends")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class ClientFriendResource {

	@Inject
	private ClientFriendService service;
	
	@Context
	UriInfo uriInfo;
	
	@POST
	public Response save(ClientFriendDTO clientFriendDTO) {
		service.save(clientFriendDTO);
		
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", clientFriendDTO.getId()).build();
		return Response.created(uri).entity(clientFriendDTO).build();
	}
	
}
