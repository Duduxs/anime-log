package org.edudev.resources;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

import org.edudev.models.Commentary;
import org.edudev.services.CommentaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

@Path("/commentaries")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
@Valid
public class CommentaryResource {

	@Inject
	private CommentaryService service;
	
	@Context
	UriInfo uriInfo;
	
	@POST
	public Response save(@Valid Commentary commentary) {
		service.save(commentary);
		
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", commentary.getId()).build();
		return Response.created(uri).entity(commentary).build();
	}
	
	@PATCH
	@Path("/{id}")
	public Response update(@Valid Commentary commentary, @PathParam(value="id")String id) {
		service.update(commentary, id);
		return Response.noContent().build();
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
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "datePost");
		Page<Commentary> commentaries = service.findAllByPaged(pageRequest);
		return Response.ok(commentaries).build();
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
