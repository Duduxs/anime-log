package org.edudev.models.dtos;

import java.util.UUID;

import org.edudev.models.Anime;
import org.edudev.models.ClientReview;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimeDTO {

	private String id = UUID.randomUUID().toString();
	
	private String imgUrl;
	private Integer episodes;
	
	private String englishTitle;

	private ClientReview review;

	public AnimeDTO() {

	}

	public AnimeDTO(String id, String imgUrl, Integer episodes, String englishTitle, ClientReview review) {
		this.id = id;
		this.imgUrl = imgUrl;
		this.episodes = episodes;
		this.englishTitle = englishTitle;
		this.review = review;
	}

	public AnimeDTO(Anime anime) {
		this.id = anime.getId();
		this.imgUrl = anime.getAnimeInfo().getImgUrl();
		this.episodes = anime.getAnimeInfo().getEpisodes();
		this.englishTitle = anime.getAnimeInfo().getEnglishTitle();
		this.review = new ClientReview(null, anime.getReview().getProgress(), anime.getReview().getTag(),
				anime.getReview().getLastEdit(), anime.getReview().getStatus(), anime.getReview().getScore());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Integer episodes) {
		this.episodes = episodes;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public ClientReview getReview() {
		return review;
	}
}
