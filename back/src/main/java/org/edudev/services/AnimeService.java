package org.edudev.services;

import java.time.LocalDateTime;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class AnimeService {

	@Inject
	private AnimeRepository repository;

	@Inject
	private ClientRepository clientRepository;

	public AnimeDTO save(Anime anime) {
		repository.save(anime);
		return toDTO(anime);
	}

	public AnimeDTO saveInUser(String user, Anime anime) {
		repository.save(anime);

		Client client = clientRepository.findByLogin(user);
		client.getAnimes().add(anime);
	
		clientRepository.save(client);
		return toDTOUser(anime);
	}

	public AnimeDTO editInUser(String id, Anime anime) {
		Anime entity = repository.getOne(id);
		return toDTOPut(entity, anime);
	}

	public void deleteInUser(String user, String id) {
		Client client = clientRepository.findByLogin(user);
		Anime anime = repository.getOne(id);

		client.getAnimes().remove(anime);
		clientRepository.saveAndFlush(client);

		anime.getAnimeGenre().clear();
		repository.deleteById(id);
	}

	public Long count() {
		return repository.count();
	}

	public Long[] countByStatus() {
		Client client = clientRepository.findByLogin("Maria");
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

	public AnimeDTO findLastAnimeEdit() {
		Client c = clientRepository.findByLogin("Maria");

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

	public Page<AnimeDTO> searchByStatusPaged(PageRequest pageRequest, AnimeStatus animeStatus) {
		Page<Anime> animes = repository.findByPagedAnimeStatus(animeStatus, pageRequest);

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

	public AnimeDTO toDTOPut(Anime entity, Anime anime) {
		entity.setStatus(anime.getStatus());
		entity.setProgress(anime.getProgress());
		entity.setScore(anime.getScore());
		entity.setLastEdit(LocalDateTime.now());
		entity.setTag(anime.getTag());

		repository.save(entity);

		return new AnimeDTO(entity, true);
	}

	public AnimeDTO toDTOLastAnimeEdit(Anime anime) {
		return new AnimeDTO(anime, 0L);
	}
}
