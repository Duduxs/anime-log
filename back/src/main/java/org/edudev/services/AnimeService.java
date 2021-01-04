package org.edudev.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.enums.AnimeStatus;
import org.edudev.models.Anime;
import org.edudev.models.Client;
import org.edudev.models.dtos.AnimeDTO;
import org.edudev.repositories.AnimeRepository;
import org.edudev.repositories.ClientRepository;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class AnimeService {

	@Inject
	private AnimeRepository repository;

	@Inject
	private ClientRepository clientRepository;


	public AnimeDTO save(Anime anime) {
		anime.setEnterDate(LocalDateTime.now());
		repository.save(anime);
		return toDTO(anime);
	}

	public AnimeDTO saveInUser(String login, Anime entity) {

		Client client = Optional.ofNullable(clientRepository.findByLogin(login)).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));
		Anime anime = Optional.ofNullable(repository.getOne(entity.getId())).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));
		
		anime.setTag(entity.getTag());
		anime.setProgress(entity.getProgress());
		anime.setLastEdit(entity.getLastEdit());
		anime.setStatus(entity.getStatus());
		anime.setScore(entity.getScore());
		anime.addMembers(1);
		
		repository.save(anime);
		
		client.getAnimes().add(anime);
		clientRepository.save(client);
		
		return toDTOUser(anime);
	}

	public AnimeDTO editInUser(String login, Anime entity) {
		repository.findById(entity.getId()).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));
		
		Client client = Optional.ofNullable(clientRepository.findByLogin(login)).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));
		Anime anime = client.getAnimes().stream().filter(a -> a.getId().equals(entity.getId())).findFirst().orElseThrow(() -> WebError.returnError(Response.Status.BAD_REQUEST, "Usuário não tem esse anime!"));
		
		anime.setStatus(entity.getStatus());
		anime.setProgress(entity.getProgress());
		anime.setScore(entity.getScore());
		anime.setLastEdit(LocalDateTime.now());
		anime.setTag(entity.getTag());

		repository.save(anime);
		
		return toDTOPut(entity);
	}

	public void deleteInUser(String login, Anime entity) {
		Client client = Optional.ofNullable(clientRepository.findByLogin(login))
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));
		
		Anime anime = repository.getOne(entity.getId());

		client.getAnimes().remove(anime);
		anime.getAnimeGenre().clear();
		
		clientRepository.saveAndFlush(client);

	
	}

	public Long count() {
		return repository.count();
	}

	public Long[] countByStatus(String login) {
		Client client = clientRepository.findByLogin(login);
		// Assistindo, Completados, Aguardando, Dropados, Pensando_em_assistir
		Long status[] = { 0L, 0L, 0L, 0L, 0L };

		for (Anime anime : client.getAnimes()) {
			switch (anime.getStatus()) {
			case ASSISTINDO:
				status[0] += 1L;
				break;
			case COMPLETADO:
				status[1] += 1L;
				break;
			case AGUARDANDO:
				status[2] += 1L;
				break;
			case DROPADO:
				status[3] += 1L;
				break;
			default:
				status[4] += 1L;
			}
		}
		return status;
	}

	public AnimeDTO findById(String id) {
		Optional<Anime> anime = Optional.ofNullable(repository.findById(id))
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));
		return toDTO(anime.get());
	}

	public AnimeDTO findLastAnimeEdit(String login) {
		Client c = Optional.ofNullable(clientRepository.findByLogin(login)).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));

		LocalDateTime newestDate = c.getAnimes().stream().map(Anime::getLastEdit).max(LocalDateTime::compareTo).get();
		Anime clientAnime = c.getAnimes().stream().filter(ca -> ca.getLastEdit().equals(newestDate)).findFirst().get();

		return toDTOLastAnimeEdit(clientAnime);
	}

	public Page<AnimeDTO> findAllPaged(PageRequest pageRequest, String isUser) {
		Page<Anime> animes = repository.findAll(pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		if (isUser.equals("true"))
			return animes.map(this::toDTOUser);

		return animes.map(this::toDTO);
	}

	public Page<AnimeDTO> findTop3AnimesPaged(PageRequest pageRequest) {
		Page<Anime> animes = repository.findAll(pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}

	public Page<AnimeDTO> findLast20AnimesPaged(PageRequest pageRequest) {
		Page<Anime> animes = repository.findAll(pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}
	
	public Page<AnimeDTO> searchLast20AnimesBetweenDatesPaged(LocalDateTime startDate, LocalDateTime endDate,
			PageRequest pageRequest) {
		Page<Anime> animes = repository.findByPagedLast20BetweenReleaseDates(startDate, endDate, pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}

	public Page<AnimeDTO> searchByTitlePaged(PageRequest pageRequest, String englishTitle, String isMainPage) {
		Page<Anime> animes = repository.findByPagedEnglishTitle(englishTitle, pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");
		if (isMainPage.equals("true"))
			return animes.map(this::toDTOMainPage);

		return animes.map(this::toDTOUser);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<AnimeDTO> searchByStatusPaged(String login, PageRequest pageRequest, AnimeStatus status) {
		Client c = Optional.ofNullable(clientRepository.findByLogin(login)).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));
		
		List<Anime> entities = c.getAnimes().stream().filter(a -> a.getStatus().equals(status)).collect(Collectors.toList());
		Page<Anime> animes = new PageImpl(entities, pageRequest, entities.size());

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOUser);
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

	public AnimeDTO toDTOPut(Anime entity) {
		return new AnimeDTO(entity, true);
	}

	public AnimeDTO toDTOLastAnimeEdit(Anime anime) {
		return new AnimeDTO(anime, 0L);
	}
}
