package org.edudev.resources;

import java.net.URI;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

import org.edudev.models.dtos.ClientDTO;
import org.edudev.services.ClientService;
import org.edudev.services.utils.TokenUtils;
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
	

	@POST
	public Response save(@Valid ClientDTO clientDTO) throws Exception {
		service.save(clientDTO);

		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", clientDTO.getId()).build();
		return Response.created(uri).entity(TokenUtils.generateTokenString("/JWTFuncionarioClaims.json", null)).build();
	}
	
	@PermitAll
	@GET
	@Path("/login")
	public Response login(
			@QueryParam("username") String username,
			@QueryParam("password") String password
			) {
		service.login(username, password);
		return Response.ok().build();
	}

	@GET
	@Path("/count")
	@RolesAllowed("admin")
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
	public Response findAllPaged(
			@Min(0)
			@QueryParam("min") Integer min, 
			@Max(30)
			@QueryParam("max") Integer max
			) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "login");
		Page<ClientDTO> clients = service.findAllByPaged(pageRequest);
		return Response.ok(clients).build();
	}

	@GET
	@Path("/online")
	public Response findLast10Online() {
		List<ClientDTO> clients = service.findLast10Online();
		return Response.ok(clients).build();
	}

	@GET
	@Path("/search")
	public Response searchByLoginPaged(@QueryParam("login") String login) {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "login");
		Page<ClientDTO> clients = service.searchByLoginPaged(pageRequest, login);
		return Response.ok(clients).build();
	}
}
