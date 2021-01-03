package org.edudev.resources;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.edudev.models.Friend;
import org.edudev.services.FriendService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

@Path("/friends")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class FriendResource {

	@Inject
	private FriendService service;
	
	@Context
	UriInfo uriInfo;
	
	@POST
	@Path("/{login}")
	public Response save(@PathParam(value="login")String login,@Valid Friend friend) {
		Friend f = service.save(login, friend);
		
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", friend.getId()).build();
		return Response.created(uri).entity(f).build();
	}
	
	@GET
	@Path("/count")
	public Response count() {
		return Response.ok(service.count()).build();
	}
	
	@GET
	public Response findAllPaged(
			@Min(0)
			@QueryParam("min") Integer min, 
			@Max(30)
			@QueryParam("max") Integer max
			) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "login");
		Page<Friend> notifications = service.findAllPaged(pageRequest);
		return Response.ok(notifications).build();
	}
	
	@GET
	@Path("/search")
	public Response searchByLoginPaged(@QueryParam("login") String login) {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "login");
		Page<Friend> friends = service.searchByLoginPaged(pageRequest, login);
		return Response.ok(friends).build();
	}
	
	@DELETE
	@Path("/{login}")
	public Response deleteByLogin(@PathParam(value="login")String login) {
		service.deleteByLogin(login);
		return Response.ok().build();
	}
	
}
