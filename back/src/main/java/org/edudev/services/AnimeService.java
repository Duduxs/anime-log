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
import org.edudev.models.AnimeInfo;
import org.edudev.models.Client;
import org.edudev.models.ClientReview;
import org.edudev.models.dtos.AnimeDTO;
import org.edudev.models.dtos.AnimeInfoDTO;
import org.edudev.repositories.AnimeInfoRepository;
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
	private AnimeInfoRepository animeInfoRepository;

	@Inject
	private ClientRepository clientRepository;

	public AnimeDTO saveInUser(Anime entity) {

		AnimeInfo animeInfo = animeInfoRepository.findByEnglishTitle(entity.getAnimeInfo().getEnglishTitle())
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não registrado no banco!"));

		animeInfo.addMembers(1);
		animeInfoRepository.save(animeInfo);

		entity.getReview().setLastEdit(LocalDateTime.now());
		repository.save(entity);

		return toDTOUser(entity);
	}

	public AnimeDTO updateInUser(String id, Anime entity) {
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

		Optional<AnimeInfo> animeInfo = animeInfoRepository.findByEnglishTitle(anime.getAnimeInfo().getEnglishTitle());

		animeInfo.get().removeMembers(1);
		animeInfoRepository.save(animeInfo.get());

		repository.delete(anime);
	}

	public Long[] countByStatus(String id) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));
		// Assistindo, Completados, Aguardando, Dropados, Pensando_em_assistir
		Long status[] = { 0L, 0L, 0L, 0L, 0L };

		for (Anime anime : client.getAnimes()) {
			switch (anime.getReview().getStatus()) {
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

	public Page<AnimeDTO> findAllInUserPaged(PageRequest pageRequest, String id) {

		Client c = clientRepository.findById(id)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));

		List<AnimeDTO> entities = c.getAnimes().stream().map(this::toDTOUser).collect(Collectors.toList());
		Page<AnimeDTO> animes = new PageImpl<AnimeDTO>(entities, pageRequest, entities.size());

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes;
	}

	public AnimeDTO findLastAnimeEdit(String id) {
		Client c = clientRepository.findById(id)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));

		if (c.getAnimes().isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		LocalDateTime newestDate = c.getAnimes().stream().map(a -> a.getReview().getLastEdit())
				.max(LocalDateTime::compareTo).get();
		Anime anime = c.getAnimes().stream().filter(a -> a.getReview().getLastEdit().equals(newestDate)).findFirst()
				.get();

		return toDTOUser(anime);
	}

	public Page<AnimeDTO> searchByStatusPaged(String id, PageRequest pageRequest, AnimeStatus status) {
		Client c = clientRepository.findById(id)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));

		List<AnimeDTO> entities = c.getAnimes().stream().filter(a -> a.getReview().getStatus().equals(status))
				.map(this::toDTOUser).collect(Collectors.toList());
		Page<AnimeDTO> animes = new PageImpl<AnimeDTO>(entities, pageRequest, entities.size());

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes;
	}

	public Page<AnimeDTO> searchByEnglishTitleInUserPaged(String id, PageRequest pageRequest, String englishTitle) {
		clientRepository.findById(id)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário não encontrado!"));
		Page<Anime> animes = repository.findByPagedEnglishTitle(id, englishTitle, pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOUser);
	}

	public AnimeInfoDTO save(AnimeInfo animeInfo) {
		animeInfo.setEnterDate(LocalDateTime.now());
		animeInfoRepository.save(animeInfo);
		return new AnimeInfoDTO(animeInfo);
	}

	public AnimeInfoDTO update(String id, AnimeInfo entity) {
		AnimeInfo animeInfo = animeInfoRepository.findById(id).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));
		AnimeInfoDTO animeInfoDTO = toDTOPut(animeInfo, entity);
		animeInfoRepository.save(animeInfo);
		return animeInfoDTO;
	}

	public Long count() {
		return animeInfoRepository.count();
	}

	public AnimeInfoDTO findByEnglishTitle(String englishTitle) {
		AnimeInfo animeInfo = animeInfoRepository.findByEnglishTitle(englishTitle)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Anime não encontrado!"));
		return toDTO(animeInfo);
	}

	public Page<AnimeInfoDTO> findAllPaged(PageRequest pageRequest) {
		Page<AnimeInfo> animes = animeInfoRepository.findAll(pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}

	public Page<AnimeInfoDTO> findTop3AnimesPaged(PageRequest pageRequest) {
		Page<AnimeInfo> animes = animeInfoRepository.findAll(pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}

	public Page<AnimeInfoDTO> findLast20AnimesPaged(PageRequest pageRequest) {
		Page<AnimeInfo> animes = animeInfoRepository.findAll(pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}

	public Page<AnimeInfoDTO> searchLast20AnimesBetweenDatesPaged(LocalDateTime startDate, LocalDateTime endDate,
			PageRequest pageRequest) {
		Page<AnimeInfo> animes = animeInfoRepository.findByPagedLast20BetweenReleaseDates(startDate, endDate,
				pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}

	public Page<AnimeInfoDTO> searchByEnglishTitlePaged(PageRequest pageRequest, String englishTitle) {
		Page<AnimeInfo> animes = animeInfoRepository.findByPagedEnglishTitle(englishTitle, pageRequest);

		if (animes.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return animes.map(this::toDTOMainPage);
	}

	public AnimeInfoDTO toDTO(AnimeInfo animeInfo) {
		return new AnimeInfoDTO(animeInfo);
	}

	public AnimeInfoDTO toDTOPut(AnimeInfo animeInfo, AnimeInfo entity) {

		animeInfo.setAuthor(entity.getAuthor());
		animeInfo.setStudio(entity.getStudio());
		animeInfo.setImgUrl(entity.getImgUrl());
		animeInfo.setVideoUrl(entity.getVideoUrl());
		animeInfo.setSinopse(entity.getSinopse());
		animeInfo.setEpisodes(entity.getEpisodes());
		animeInfo.setEnglishTitle(entity.getEnglishTitle());
		animeInfo.setJapaneseTitle(entity.getJapaneseTitle());
		animeInfo.setPortugueseTitle(entity.getPortugueseTitle());
		animeInfo.setReleaseDate(entity.getReleaseDate());
		animeInfo.setTvHour(entity.getTvHour());
		animeInfo.setDuration(entity.getDuration());
		animeInfo.getAnimeGenre().clear();
		animeInfo.getAnimeGenre().addAll(entity.getAnimeGenre());
		animeInfo.setAnimePublic(entity.getAnimePublic());

		return new AnimeInfoDTO(animeInfo);
	}

	public AnimeInfoDTO toDTOMainPage(AnimeInfo animeInfo) {
		return new AnimeInfoDTO(animeInfo.getId(), null, null, null, animeInfo.getImgUrl(), null, null, null,
				animeInfo.getEnglishTitle(), null, null, null, null, null, null, null);
	}

	public AnimeDTO toDTO(Anime anime) {
		return new AnimeDTO(anime);
	}

	public AnimeDTO toDTOUser(Anime anime) {
		return new AnimeDTO(anime.getId(), anime.getAnimeInfo().getImgUrl(), anime.getAnimeInfo().getEpisodes(),
				anime.getAnimeInfo().getEnglishTitle(),
				new ClientReview(anime.getId(), anime.getReview().getProgress(), anime.getReview().getTag(),
						anime.getReview().getLastEdit(), anime.getReview().getStatus(), anime.getReview().getScore()));
	}
}
