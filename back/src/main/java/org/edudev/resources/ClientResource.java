package org.edudev.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.dtos.ClientPostDTO;
import org.edudev.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		Client client = service.findById(id);
		return Response.ok(client).build();
	}
	
	@GET
	public Response findAllPaged(
			@QueryParam("min") Integer min, 
			@QueryParam("max") Integer max) {
		Page<Client> clients = service.findAllByPaged(PageRequest.of(min, max, Sort.Direction.ASC, "login"));
		return Response.ok(clients).build();
	}
	
	@GET
	@Path("/search")
	public Response searchByLogin(@QueryParam("login") String login) {
		Page<Client> clients = service.searchByLogin(login);
		return Response.ok(clients).build();
	}
	
	@GET
	@Path("/login")
	public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
		service.login(username, password);
		return Response.ok().build();		
	}
	
	@POST
	public Response save(ClientPostDTO clientDTO) {
		clientDTO = service.save(clientDTO);
		return Response.ok(clientDTO).status(Response.Status.CREATED).build();
	}
	
	
}
