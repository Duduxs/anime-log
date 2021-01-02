package org.edudev.resources;

import java.net.URI;
import java.time.LocalDateTime;

import javax.inject.Inject;
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
import org.edudev.models.Client;
import org.edudev.models.dtos.AnimeDTO;
import org.edudev.services.AnimeService;
import org.jboss.logging.Logger;
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
	
	private static final Logger LOG = Logger.getLogger(Client.class);

	@GET
	@Path("/count")
	public Response count() {
		return Response.ok(service.count()).build();
	}
	
	@GET
	@Path("/count/status")
	public Response countByAnimeStatus() {
		return Response.ok(service.countByAnimeStatus()).build();
	}
	
	@GET
	@Path("/lastEdit")
	public Response findLastEditAnime() {
		AnimeDTO animeDTO = service.showLastAnimeEdit();
		return Response.ok(animeDTO).build();
	}
	
	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") String id) {
		AnimeDTO animeDTO = service.findById(id);
		return Response.ok(animeDTO).build();
	}

	@GET
	public Response findAllPaged(@QueryParam("min") Integer min, @QueryParam("max") Integer max,
			@QueryParam("isUser") Integer isUser) {
		Page<AnimeDTO> animesDTO = service.findAllByPaged(PageRequest.of(min, max, Sort.Direction.ASC, "englishTitle"),
				isUser);
		return Response.ok(animesDTO).build();
	}

	@GET
	@Path("/search")
	public Response searchByTitle(@QueryParam("title") String title, @QueryParam("isMainPage") Integer isMainPage) {
		Page<AnimeDTO> clients = service.searchByTitle(PageRequest.of(0, 10, Sort.Direction.ASC, "englishTitle"), title,
				isMainPage);
		return Response.ok(clients).build();
	}
	
	@GET
	@Path("/search/status")
	public Response searchByAnimeStatus(@QueryParam("animeStatus") AnimeStatus animeStatus) {
		Page<AnimeDTO> clients = service.searchByAnimeStatus(PageRequest.of(0, 10, Sort.Direction.ASC, "englishTitle"), animeStatus);	
		return Response.ok(clients).build();
	}
	
	@GET
	@Path("/top3")
	public Response getTop3Animes(@QueryParam("orderBy") String orderBy) {
		Page<AnimeDTO> animes = service.getTop3Animes(PageRequest.of(0, 3, Sort.Direction.valueOf(orderBy), "members"));
		return Response.ok(animes).build();
	}

	@GET
	@Path("/last20")
	public Response getLast20Animes() {
		Page<AnimeDTO> animes = service.getLast20Animes(PageRequest.of(0, 20, Sort.Direction.ASC, "enterDate"));
		return Response.ok(animes).build();
	}

	@GET
	@Path("/period/last20")
	public Response getLast20AnimesBetweenDates(@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {

		Integer startDateDay = Integer.parseInt(startDate.substring(0, 2));
		Integer startDateMonth = Integer.parseInt(startDate.substring(3, 5));
		Integer startDateYear = Integer.parseInt(startDate.substring(6,10));

		Integer endDateDay = Integer.parseInt(endDate.substring(0, 2));
		Integer endDateMonth = Integer.parseInt(endDate.substring(3, 5));
		Integer endDateYear = Integer.parseInt(endDate.substring(6,10));

		Page<AnimeDTO> animes = service.getLast20StationAnimes(
				LocalDateTime.of(startDateYear, startDateMonth, startDateDay, 0, 0),
				LocalDateTime.of(endDateYear, endDateMonth, endDateDay, 0, 0),
				PageRequest.of(0, 20, Sort.Direction.ASC, "englishTitle"));
		return Response.ok(animes).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response edit(@PathParam("id")String id, Anime anime) {
		AnimeDTO animeDTO = service.edit(id, anime);
		return Response.ok(animeDTO).build();
	}

	@POST
	public Response save(Anime anime) {
		AnimeDTO animeDTO = service.save(anime);
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", animeDTO.getId()).build();
		return Response.created(uri).entity(animeDTO).build();
	}
	
	@POST
	@Path("/{user}")
	public Response saveInUser(@PathParam("user")String user, Anime anime) {
		AnimeDTO animeDTO = service.saveInUser(user, anime);
		URI uri = uriInfo.getAbsolutePathBuilder().path("{id}").resolveTemplate("id", animeDTO.getId()).build();
		return Response.created(uri).entity(animeDTO).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteInUser(@PathParam("id") String id) {
		service.deleteInUser("Maria", id);
		return Response.noContent().build();
	}
}
