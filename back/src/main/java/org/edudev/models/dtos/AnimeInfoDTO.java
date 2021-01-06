package org.edudev.models.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

import org.edudev.enums.AnimeGenre;
import org.edudev.enums.AnimePublic;
import org.edudev.models.AnimeInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimeInfoDTO {

	@Id
	private String id;

	@PositiveOrZero
	private Integer members;

	private String author;
	private String studio;

	private String imgUrl;
	private String videoUrl;

	private String sinopse;
	private Integer episodes;

	private String englishTitle;
	private String japaneseTitle;
	private String portugueseTitle;

	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime releaseDate;
	private String tvHour;
	private String duration;

	private Set<AnimeGenre> animeGenre = new HashSet<>();

	private AnimePublic animePublic;

	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime enterDate;

	public AnimeInfoDTO() {

	}

	public AnimeInfoDTO(String id, Integer members, String author, String studio, String imgUrl,
			String videoUrl, String sinopse, Integer episodes, String englishTitle, String japaneseTitle,
			String portugueseTitle, LocalDateTime releaseDate, String tvHour, String duration, AnimePublic animePublic,
			LocalDateTime enterDate) {
		this.id = id;
		this.members = members;
		this.author = author;
		this.studio = studio;
		this.imgUrl = imgUrl;
		this.videoUrl = videoUrl;
		this.sinopse = sinopse;
		this.episodes = episodes;
		this.englishTitle = englishTitle;
		this.japaneseTitle = japaneseTitle;
		this.portugueseTitle = portugueseTitle;
		this.releaseDate = releaseDate;
		this.tvHour = tvHour;
		this.duration = duration;
		this.animePublic = animePublic;
		this.enterDate = enterDate;
	}
	
	public AnimeInfoDTO(AnimeInfo animeInfo) {
		this.id = animeInfo.getId();
		this.members = animeInfo.getMembers();
		this.author = animeInfo.getAuthor();
		this.studio = animeInfo.getStudio();
		this.imgUrl = animeInfo.getImgUrl();
		this.videoUrl = animeInfo.getVideoUrl();
		this.sinopse = animeInfo.getSinopse();
		this.episodes = animeInfo.getEpisodes();
		this.englishTitle = animeInfo.getEnglishTitle();
		this.japaneseTitle = animeInfo.getJapaneseTitle();
		this.portugueseTitle = animeInfo.getPortugueseTitle();
		this.releaseDate = animeInfo.getReleaseDate();
		this.tvHour = animeInfo.getTvHour();
		this.duration = animeInfo.getDuration();
		this.animePublic = animeInfo.getAnimePublic();
		this.enterDate = animeInfo.getEnterDate();
		this.animeGenre.addAll(animeInfo.getAnimeGenre());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMembers() {
		return members;
	}

	public void setMembers(Integer members) {
		this.members = members;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
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

	public String getJapaneseTitle() {
		return japaneseTitle;
	}

	public void setJapaneseTitle(String japaneseTitle) {
		this.japaneseTitle = japaneseTitle;
	}

	public String getPortugueseTitle() {
		return portugueseTitle;
	}

	public void setPortugueseTitle(String portugueseTitle) {
		this.portugueseTitle = portugueseTitle;
	}

	public LocalDateTime getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDateTime releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getTvHour() {
		return tvHour;
	}

	public void setTvHour(String tvHour) {
		this.tvHour = tvHour;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Set<AnimeGenre> getAnimeGenre() {
		return animeGenre;
	}

	public AnimePublic getAnimePublic() {
		return animePublic;
	}

	public void setAnimePublic(AnimePublic animePublic) {
		this.animePublic = animePublic;
	}

	public LocalDateTime getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(LocalDateTime enterDate) {
		this.enterDate = enterDate;
	}

}
