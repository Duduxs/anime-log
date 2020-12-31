package org.edudev.services;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Anime;
import org.edudev.models.dtos.AnimeDTO;
import org.edudev.repositories.AnimeRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class AnimeService {

	private static final Logger LOG = Logger.getLogger(Anime.class);

	@Inject
	private AnimeRepository repository;

	@Inject
	private Validator validator;

	public AnimeDTO findById(String id) {
		Optional<Anime> anime = Optional.ofNullable(repository.findById(id)).orElseThrow(
				() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));
		return toDTO(anime.get());
	}
	
	public Long count() {
		return repository.count();
	}
	
	public Page<AnimeDTO> findAllByPaged(PageRequest pageRequest, Integer isUser) {
		Page<Anime> animes = repository.findAll(pageRequest);
		
		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");
		
		if(isUser == 0) 
			return animes.map(this::toDTOUser); 
		
		return animes.map(this::toDTO);
	}
	
	public Page<AnimeDTO> searchByTitle(PageRequest pageRequest, String englishTitle, Integer isMainPage) {
		Page<Anime> animes = repository.findByPagedEnglishTitle(englishTitle, pageRequest);
		
		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");
		if(isMainPage == 0)
			return animes.map(this::toDTOMainPage);
		
		return animes.map(this::toDTO);
	}
	
	public Page<AnimeDTO> getLast20Animes(PageRequest pageRequest) {
		Page<Anime> animes = repository.findAll(pageRequest);
		
		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}
	
	public Page<AnimeDTO> getLast20StationAnimes(LocalDateTime startDate, LocalDateTime endDate, PageRequest pageRequest) {
		Page<Anime> animes = repository.findByPagedLast20BetweenReleaseDates(startDate,endDate, pageRequest);
		
		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}
	
	public AnimeDTO save(Anime anime) {
		repository.save(anime);
		return toDTO(anime);
	}
	
	public AnimeDTO toDTO(Anime anime) {
		return new AnimeDTO(anime);
	}
	
	public AnimeDTO toDTOUser(Anime anime) {
		return new AnimeDTO(anime, "");
	}
	
	public AnimeDTO toDTOMainPage(Anime anime) {
		return new AnimeDTO(anime, 0);
	}
}
