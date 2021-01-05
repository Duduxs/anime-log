package org.edudev.services;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Anime;
import org.edudev.models.AnimeInfo;
import org.edudev.models.ClientReview;
import org.edudev.models.dtos.AnimeDTO;
import org.edudev.models.dtos.AnimeInfoDTO;
import org.edudev.repositories.AnimeInfoRepository;
import org.edudev.repositories.AnimeRepository;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class AnimeService {

	@Inject
	private AnimeRepository repository;

	@Inject
	private AnimeInfoRepository animeInfoRepository;

	public AnimeInfoDTO save(AnimeInfo animeInfo) {
		animeInfo.setEnterDate(LocalDateTime.now());
		animeInfoRepository.save(animeInfo);
		return new AnimeInfoDTO(animeInfo);
	}

	public AnimeDTO saveInUser(Anime entity) {

		AnimeInfo animeInfo = animeInfoRepository.findByEnglishTitle(entity.getAnimeInfo().getEnglishTitle())
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não registrado no banco!"));

		animeInfo.addMembers(1);
		animeInfoRepository.save(animeInfo);

		repository.save(entity);
		return toDTOUser(entity);
	}

	public AnimeDTO editInUser(String id, Anime entity) {
		Anime anime = repository.findById(id)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));

		anime.getReview().setProgress(entity.getReview().getProgress());
		anime.getReview().setTag(entity.getReview().getTag());
		anime.getReview().setLastEdit(LocalDateTime.now());
		anime.getReview().setStatus(entity.getReview().getStatus());
		anime.getReview().setScore(entity.getReview().getScore());

		repository.save(anime);
		return toDTOUser(anime);
	}

	public void deleteInUser(String id) {
		Anime anime = repository.findById(id)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));
		
		repository.delete(anime);
	}

	public Long count() {
		return repository.count();
	}

//	public Long[] countByStatus(String login) {
//		Client client = clientRepository.findByLogin(login);
//		// Assistindo, Completados, Aguardando, Dropados, Pensando_em_assistir
//		Long status[] = { 0L, 0L, 0L, 0L, 0L };
//
//		for (Anime anime : client.getAnimes()) {
//			switch (anime.getStatus()) {
//			case ASSISTINDO:
//				status[0] += 1L;
//				break;
//			case COMPLETADO:
//				status[1] += 1L;
//				break;
//			case AGUARDANDO:
//				status[2] += 1L;
//				break;
//			case DROPADO:
//				status[3] += 1L;
//				break;
//			default:
//				status[4] += 1L;
//			}
//		}
//		return status;
//	}

	public AnimeDTO findById(String id) {
		Optional<Anime> anime = Optional.ofNullable(repository.findById(id))
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));
		return toDTO(anime.get());
	}
//
//	public AnimeDTO findLastAnimeEdit(String login) {
//		Client c = Optional.ofNullable(clientRepository.findByLogin(login)).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));
//
//		LocalDateTime newestDate = c.getAnimes().stream().map(Anime::getLastEdit).max(LocalDateTime::compareTo).get();
//		Anime clientAnime = c.getAnimes().stream().filter(ca -> ca.getLastEdit().equals(newestDate)).findFirst().get();
//
//		return toDTOLastAnimeEdit(clientAnime);
//	}

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

//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public Page<AnimeDTO> searchByStatusPaged(String login, PageRequest pageRequest, AnimeStatus status) {
//		Client c = Optional.ofNullable(clientRepository.findByLogin(login)).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));
//		
//		List<Anime> entities = c.getAnimes().stream().filter(a -> a.getStatus().equals(status)).collect(Collectors.toList());
//		Page<Anime> animes = new PageImpl(entities, pageRequest, entities.size());
//
//		if (animes.isEmpty())
//			WebError.sendError(Response.Status.NO_CONTENT, "");
//
//		return animes.map(this::toDTOUser);
//	}

	public AnimeDTO toDTO(Anime anime) {
		return new AnimeDTO(anime);
	}

	public AnimeDTO toDTOUser(Anime anime) {
		return new AnimeDTO(anime.getId(), anime.getAnimeInfo().getImgUrl(), anime.getAnimeInfo().getEpisodes(),
				anime.getAnimeInfo().getEnglishTitle(),
				new ClientReview(null, anime.getReview().getProgress(), anime.getReview().getTag(),
						anime.getReview().getLastEdit(), anime.getReview().getStatus(), anime.getReview().getScore()));
	}
	
	public AnimeDTO toDTOMainPage(Anime anime) {
		return new AnimeDTO(anime, 0);
	}

	public AnimeDTO toDTOLastAnimeEdit(Anime anime) {
		return new AnimeDTO(anime, 0L);
	}
}
