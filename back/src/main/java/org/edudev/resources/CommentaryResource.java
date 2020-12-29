package org.edudev.resources;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.edudev.models.dtos.CommentaryDTO;
import org.edudev.services.CommentaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

@Path("/commentaries")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class CommentaryResource {

	@Inject
	private CommentaryService service;
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path("/count")
	public Response count() {
		return Response.ok(service.count()).build();
	}
	
	@GET
	public Response findAllPaged(
			@QueryParam("min") Integer min, 
			@QueryParam("max") Integer max) {
		
		Page<CommentaryDTO> commentaries = service.findAllByPaged(PageRequest.of(min, max, Sort.Direction.ASC, "datePost"));
		return Response.ok(commentaries).build();
	}
	
	@POST
	public Response save(CommentaryDTO commentary) {
		service.save(commentary);
		
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", commentary.getId()).build();
		return Response.created(uri).entity(commentary).build();
	}
	
	@PATCH
	@Path("/{id}")
	public Response update(CommentaryDTO commentary, @PathParam(value="id")String id) {
		service.update(commentary, id);
		return Response.noContent().build();
	}


	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam(value="id")String id) {
		service.deleteById(id);
		return Response.ok().build();
	}
	
	@DELETE
	public Response deleteAll() {
		service.deleteAll();
		return Response.ok().build();
	}
}
