package org.edudev.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.PositiveOrZero;

import org.edudev.enums.AnimeGenre;
import org.edudev.enums.AnimePublic;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class AnimeInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id = UUID.randomUUID().toString();
	
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
	
	@ElementCollection(targetClass = AnimeGenre.class)
	@Column(name = "genre", nullable = false)
    @CollectionTable(name="anime_genre",  joinColumns = @JoinColumn(name = "anime_id"))
	@Enumerated(EnumType.STRING)
	private Set<AnimeGenre> animeGenre = new HashSet<>();
	
	private AnimePublic animePublic;

	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime enterDate;
	
	public AnimeInfo() {
		
	}

	public AnimeInfo(String id, Integer members, String author, String studio, String imgUrl, String videoUrl,
			String sinopse, Integer episodes, String englishTitle, String japaneseTitle, String portugueseTitle,
			LocalDateTime releaseDate, String tvHour, String duration,
			AnimePublic animePublic, LocalDateTime enterDate) {
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

	public void addMembers(Integer members) {
	this.members+= members;
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
