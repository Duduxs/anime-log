package org.edudev.models.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.edudev.enums.AnimeGenre;
import org.edudev.enums.AnimePublic;
import org.edudev.enums.AnimeScore;
import org.edudev.enums.AnimeStatus;
import org.edudev.models.Anime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimeDTO {

	private String id = UUID.randomUUID().toString();
	
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
	
	private Integer progress;
	private String tag;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime lastEdit;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime enterDate = LocalDateTime.now();

	private AnimeStatus status;
	private AnimeScore score;

	public AnimeDTO() {
		
	}

	public AnimeDTO(String id, Integer members, String title, String author, String studio, String imgUrl,
			String videoUrl, String sinopse, Integer episodes, String englishTitle, String japaneseTitle,
			String portugueseTitle, LocalDateTime releaseDate, String tvHour, String duration,
			Set<AnimeGenre> animeGenre, AnimePublic animePublic, Integer progress, String tag, LocalDateTime lastEdit, LocalDateTime enterDate,
			AnimeStatus status, AnimeScore score) {
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
		this.animeGenre = animeGenre;
		this.animePublic = animePublic;
		this.progress = progress;
		this.tag = tag;
		this.lastEdit = lastEdit;
		this.enterDate = enterDate;
		this.status = status;
		this.score = score;
	}
	
	public AnimeDTO(Anime anime) {
		this.id = anime.getId();
		this.members = anime.getMembers();
		this.author = anime.getAuthor();
		this.studio = anime.getStudio();
		this.imgUrl = anime.getImgUrl();
		this.videoUrl = anime.getVideoUrl();
		this.sinopse = anime.getSinopse();
		this.episodes = anime.getEpisodes();
		this.englishTitle = anime.getEnglishTitle();
		this.japaneseTitle = anime.getJapaneseTitle();
		this.portugueseTitle = anime.getPortugueseTitle();
		this.releaseDate = anime.getReleaseDate();
		this.tvHour = anime.getTvHour();
		this.duration = anime.getDuration();
		this.animeGenre = anime.getAnimeGenre();
		this.animePublic = anime.getAnimePublic();
	}
	
	public AnimeDTO(Anime anime, String userInfo) {
		this.imgUrl = anime.getImgUrl();
		this.englishTitle = anime.getEnglishTitle();
		this.episodes = anime.getEpisodes();
		
		this.progress = anime.getProgress();
		this.tag = anime.getTag();
		this.lastEdit = anime.getLastEdit();
		this.status = anime.getStatus();
		this.score = anime.getScore();
	}
	
	public AnimeDTO(Anime anime, Integer mainDTO) {
		this.englishTitle = anime.getEnglishTitle();
		this.imgUrl = anime.getImgUrl();
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

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public LocalDateTime getLastEdit() {
		return lastEdit;
	}

	public void setLastEdit(LocalDateTime lastEdit) {
		this.lastEdit = lastEdit;
	}
	
	public LocalDateTime getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(LocalDateTime enterDate) {
		this.enterDate = enterDate;
	}

	public AnimeStatus getStatus() {
		return status;
	}

	public void setStatus(AnimeStatus status) {
		this.status = status;
	}

	public AnimeScore getScore() {
		return score;
	}

	public void setScore(AnimeScore score) {
		this.score = score;
	}

	
}
