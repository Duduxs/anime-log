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
import org.edudev.models.dtos.AnimeDTO;
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
	public Response save(@Valid Anime anime) {
		AnimeDTO animeDTO = service.save(anime);
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", animeDTO.getId()).build();
		return Response.created(uri).entity(animeDTO).build();
	}

	@POST
	@Path("/{login}")
	public Response saveInUser(@PathParam("login") String login, @Valid Anime anime) {
		AnimeDTO animeDTO = service.saveInUser(login, anime);
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", animeDTO.getId()).build();
		return Response.created(uri).entity(animeDTO).build();
	}

	@PUT
	@Path("/{id}")
	public Response editInUser(@PathParam("id") String id, @Valid Anime anime) {
		AnimeDTO animeDTO = service.editInUser(id, anime);
		return Response.ok(animeDTO).build();
	}

	@DELETE
	@Path("/{login}")
	public Response deleteInUser(@PathParam("login") String login, @Valid Anime anime) {
		service.deleteInUser(login, anime);
		return Response.noContent().build();
	}

	@GET
	@Path("/count")
	public Response count() {
		return Response.ok(service.count()).build();
	}

	@GET
	@Path("/count/status/{login}")
	public Response countByStatus(@PathParam("login") String login) {
		return Response.ok(service.countByStatus(login)).build();
	}

	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") String id) {
		AnimeDTO animeDTO = service.findById(id);
		return Response.ok(animeDTO).build();
	}
	
	@GET
	@Path("/lastEdit/{login}")
	public Response findLastEditAnime(@PathParam("login") String login) {
		AnimeDTO animeDTO = service.findLastAnimeEdit(login);
		return Response.ok(animeDTO).build();
	}

	@GET
	public Response findAllPaged(
			@Min(0)
			@QueryParam("min") Integer min, 
			@Max(2000)
			@QueryParam("max") Integer max,
			@QueryParam("isUser") String isUser
			) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle");
		Page<AnimeDTO> animesDTO = service.findAllPaged(pageRequest, isUser);
		return Response.ok(animesDTO).build();
	}
	
	@GET
	@Path("/top3")
	public Response findTop3AnimesPaged(@QueryParam("orderBy") String orderBy) {
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.valueOf(orderBy), "members");
		Page<AnimeDTO> animes = service.findTop3AnimesPaged(pageRequest);
		return Response.ok(animes).build();
	}

	@GET
	@Path("/last20")
	public Response findLast20AnimesPaged() {
		PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.ASC, "enterDate");
		Page<AnimeDTO> animes = service.findLast20AnimesPaged(pageRequest);
		return Response.ok(animes).build();
	}
	
	@GET
	@Path("/period/last20")
	public Response searchLast20AnimesBetweenDatesPaged(
			@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate
			) {

		Integer startDateDay = Integer.parseInt(startDate.substring(0, 2));
		Integer startDateMonth = Integer.parseInt(startDate.substring(3, 5));
		Integer startDateYear = Integer.parseInt(startDate.substring(6, 10));

		Integer endDateDay = Integer.parseInt(endDate.substring(0, 2));
		Integer endDateMonth = Integer.parseInt(endDate.substring(3, 5));
		Integer endDateYear = Integer.parseInt(endDate.substring(6, 10));

		Page<AnimeDTO> animes = service.searchLast20AnimesBetweenDatesPaged(
				LocalDateTime.of(startDateYear, startDateMonth, startDateDay, 0, 0),
				LocalDateTime.of(endDateYear, endDateMonth, endDateDay, 0, 0),
				PageRequest.of(0, 20, Sort.Direction.ASC, "englishTitle"));
		return Response.ok(animes).build();
	}

	@GET
	@Path("/search")
	public Response searchByTitlePaged(
			@Min(0)
			@QueryParam("min") Integer min, 
			@Max(2000)
			@QueryParam("max") Integer max,
			@QueryParam("title") String title, 
			@QueryParam("isMainPage") String isMainPage
			) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle");
		Page<AnimeDTO> clients = service.searchByTitlePaged(pageRequest, title, isMainPage);
		return Response.ok(clients).build();
	}

	@GET
	@Path("/search/status/{login}")
	public Response searchByStatusPaged(
			@PathParam("login")String login, 
			@Min(0)
			@QueryParam("min") Integer min, 
			@Max(2000)
			@QueryParam("max") Integer max,
			@QueryParam("status") AnimeStatus status) {
		PageRequest pageRequest = PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle");
		Page<AnimeDTO> clients = service.searchByStatusPaged(login, pageRequest, status);
		return Response.ok(clients).build();
	}
	
}
