package org.edudev.resources;

import java.net.URI;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.edudev.enums.AnimeStatus;
import org.edudev.models.Anime;
import org.edudev.models.AnimeInfo;
import org.edudev.models.dtos.AnimeDTO;
import org.edudev.models.dtos.AnimeInfoDTO;
import org.edudev.services.AnimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

@Path("/animes")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class AnimeResource {

	@Inject
	private AnimeService service;

	@Context
	UriInfo uriInfo;

	@POST
	@Path("/user")
	public Response saveInUser(@Valid Anime anime) {
		AnimeDTO animeDTO = service.saveInUser(anime);
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", animeDTO.getId()).build();
		return Response.created(uri).entity(animeDTO).build();
	}

	@PUT
	@Path("/user/animeId/{id}")
	public Response updateInUser(@PathParam("id") String id, @Valid Anime anime) {
		AnimeDTO animeDTO = service.updateInUser(id, anime);
		return Response.ok(animeDTO).build();
	}

	@DELETE
	@Path("/user/animeId/{id}")
	public Response deleteInUser(@PathParam("id") String id) {
		service.deleteInUser(id);
		return Response.noContent().build();
	}

	@GET
	@Path("/user/count/{id}")
	public Response countByStatus(@PathParam("id") String id) {
		return Response.ok(service.countByStatus(id)).build();
	}
	
	@GET
	@Path("/user/{id}")
	public Response findAllInUserPaged(@Min(0) @QueryParam("min") Integer min,
			@Max(2000) @QueryParam("max") Integer max, @PathParam("id") String id) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle");
		Page<AnimeDTO> animesDTO = service.findAllInUserPaged(pageRequest, id);
		return Response.ok(animesDTO).build();
	}

	@GET
	@Path("/user/lastEdit/{id}")
	public Response findLastEditAnime(@PathParam("id") String id) {
		AnimeDTO animeDTO = service.findLastAnimeEdit(id);
		return Response.ok(animeDTO).build();
	}

	@GET
	@Path("/user/search/{id}")
	public Response searchByStatusPaged(@PathParam("id") String id, @Min(0) @QueryParam("min") Integer min,
			@Max(2000) @QueryParam("max") Integer max, @QueryParam("status") AnimeStatus status) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle");
		Page<AnimeDTO> clients = service.searchByStatusPaged(id, pageRequest, status);
		return Response.ok(clients).build();
	}
	
	@GET
	@Path("/user/search/{id}/{englishTitle}")
	public Response searchByEnglishTitleInUserPaged(
			@PathParam("id") String id,
			@PathParam("englishTitle") String englishTitle,
			@Min(0)
			@QueryParam("min") Integer min, 
			@Max(2000)
			@QueryParam("max") Integer max
			) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle");
		Page<AnimeDTO> animesDTO= service.searchByEnglishTitleInUserPaged(id, pageRequest, englishTitle);
		return Response.ok(animesDTO).build();
	}

	@POST
	public Response save(@Valid AnimeInfo animeInfo) {
		AnimeInfoDTO animeInfoDTO = service.save(animeInfo);
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", animeInfoDTO.getId()).build();
		return Response.created(uri).entity(animeInfoDTO).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") String id, @Valid AnimeInfo animeInfo) {
		AnimeInfoDTO animeInfoDTO = service.update(id, animeInfo);
		return Response.ok(animeInfoDTO).build();
	}

	@GET
	@Path("/count")
	public Response count() {
		return Response.ok(service.count()).build();
	}

	@GET
	@Path("/{englishTitle}")
	public Response findByEnglishTitle(@PathParam("englishTitle") String englishTitle) {
		AnimeInfoDTO animeInfoDTO = service.findByEnglishTitle(englishTitle);
		return Response.ok(animeInfoDTO).build();
	}

	@GET
	public Response findAllPaged(@Min(0) @QueryParam("min") Integer min, @Max(2000) @QueryParam("max") Integer max) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle");
		Page<AnimeInfoDTO> animesInfoDTO = service.findAllPaged(pageRequest);
		return Response.ok(animesInfoDTO).build();
	}

	@GET
	@Path("/top3")
	public Response findTop3AnimesPaged(@QueryParam("orderBy") String orderBy) {
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.valueOf(orderBy), "members");
		Page<AnimeInfoDTO> animesInfoDTO = service.findTop3AnimesPaged(pageRequest);
		return Response.ok(animesInfoDTO).build();
	}

	@GET
	@Path("/last20")
	public Response findLast20AnimesPaged() {
		PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.ASC, "enterDate");
		Page<AnimeInfoDTO> animesInfoDTO = service.findLast20AnimesPaged(pageRequest);
		return Response.ok(animesInfoDTO).build();
	}

	@GET
	@Path("/period/last20")
	public Response searchLast20AnimesBetweenDatesPaged(@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {

		LocalDateTime start = LocalDateTime.of(
				Integer.parseInt(startDate.substring(0, 2)),
				Integer.parseInt(startDate.substring(3, 5)), 
				Integer.parseInt(startDate.substring(6, 10)), 0, 0);

		LocalDateTime end = LocalDateTime.of(
				Integer.parseInt(endDate.substring(0, 2)),
				Integer.parseInt(endDate.substring(3, 5)), 
				Integer.parseInt(endDate.substring(6, 10)), 0, 0);

		Page<AnimeInfoDTO> animesInfoDTO = service.searchLast20AnimesBetweenDatesPaged(
				start, 
				end,
				PageRequest.of(0, 20, Sort.Direction.ASC, "englishTitle"));
		return Response.ok(animesInfoDTO).build();
	}

	@GET
	@Path("/search")
	public Response searchByEnglishTitlePaged(
			@Min(0)
			@QueryParam("min") Integer min, 
			@Max(2000)
			@QueryParam("max") Integer max,
			@QueryParam("englishTitle") String englishTitle
			) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle");
		Page<AnimeInfoDTO> animesInfoDTO= service.searchByEnglishTitlePaged(pageRequest, englishTitle);
		return Response.ok(animesInfoDTO).build();
	}

}
