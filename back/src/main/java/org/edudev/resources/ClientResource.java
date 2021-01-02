package org.edudev.resources;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.edudev.models.dtos.AnimeDTO;
import org.edudev.models.dtos.ClientDTO;
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
	
	@Context
	UriInfo uriInfo;

	
	@GET
	@Path("/count")
	public Response count() {
		return Response.ok(service.count()).build();
	}
		
	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") String id) {
		ClientDTO client = service.findById(id);
		return Response.ok(client).build();
	}
	
	@GET
	@Path("/online")
	public Response usersOnline() {
		List<ClientDTO> clients = service.findLast10UsersOnline();
		return Response.ok(clients).build();
	}
	
	@GET
	public Response findAllPaged(
			@QueryParam("min") Integer min, 
			@QueryParam("max") Integer max) {
		Page<ClientDTO> clients = service.findAllByPaged(PageRequest.of(min, max, Sort.Direction.ASC, "login"));
		return Response.ok(clients).build();
	}
	
	@GET
	@Path("/search")
	public Response searchByLogin(@QueryParam("login") String login) {
		Page<ClientDTO> clients = service.searchByLogin(login);
		return Response.ok(clients).build();
	}
	
	@GET
	@Path("/login")
	public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
		service.login(username, password);
		return Response.ok().build();		
	}
	
	@POST
	public Response save(ClientDTO clientDTO) {
		service.save(clientDTO);
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", clientDTO.getId()).build();
		return Response.created(uri).entity(clientDTO).build();
	}
	
	
}
