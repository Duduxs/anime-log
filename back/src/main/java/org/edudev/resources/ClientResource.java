package org.edudev.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.dtos.ClientPostDTO;
import org.edudev.services.ClientService;
import org.springframework.http.MediaType;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class ClientResource {

	@Inject
	private ClientService service;

	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") String id) {
		Client c = service.findById(id);
		return Response.ok(c).build();
	}
	
	@POST
	public Response save(ClientPostDTO clientDTO) {
		clientDTO = service.save(clientDTO);
		return Response.ok(clientDTO).status(Response.Status.CREATED).build();
	}
	
	
}
