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
import org.edudev.enums.AnimeScore;
import org.edudev.enums.AnimeStatus;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Anime implements Serializable {

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
	private String JapaneseTitle;
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
	
	@Range(min = 0, max = 5000)
	private Integer progress;
	@Length(min = 0, max = 10000)
	private String tag;
	
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime lastEdit;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime enterDate;

	private AnimeStatus status;
	private AnimeScore score;
	
	public Anime() {
		
	}

	public Anime(String id, Integer members, String title, String author, String studio, String imgUrl, String videoUrl,
			String sinopse, Integer episodes, String englishTitle, String japaneseTitle, String portugueseTitle,
			LocalDateTime releaseDate, String tvHour, String duration, Set<AnimeGenre> animeGenre,
			AnimePublic animePublic, Integer progress, String tag, LocalDateTime lastEdit, LocalDateTime enterDate, AnimeStatus status,
			AnimeScore score) {
		this.id = id;
		this.members = members;
		this.author = author;
		this.studio = studio;
		this.imgUrl = imgUrl;
		this.videoUrl = videoUrl;
		this.sinopse = sinopse;
		this.episodes = episodes;
		this.englishTitle = englishTitle;
		JapaneseTitle = japaneseTitle;
		this.portugueseTitle = portugueseTitle;
		this.releaseDate = releaseDate;
		this.tvHour = tvHour;
		this.duration = duration;
		this.animePublic = animePublic;
		this.progress = progress;
		this.tag = tag;
		this.lastEdit = lastEdit;
		this.enterDate = enterDate;
		this.status = status;
		this.score = score;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return JapaneseTitle;
	}

	public void setJapaneseTitle(String japaneseTitle) {
		JapaneseTitle = japaneseTitle;
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

	public Integer getMembers() {
		return members;
	}

	public void setMembers(Integer members) {
		this.members = members;
	}
	
	public void addMembers(Integer members) {
		this.members+= members;
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
	
	public void setAnimeGenre(Set<AnimeGenre> animeGenre) {
		this.animeGenre = animeGenre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anime other = (Anime) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
