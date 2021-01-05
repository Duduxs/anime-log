package org.edudev.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.edudev.enums.AnimeScore;
import org.edudev.enums.AnimeStatus;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "client_review")
public class ClientReview {

	@Id
	private String id = UUID.randomUUID().toString();

	@Range(min = 0, max = 5000)
	private Integer progress;
	@Length(min = 0, max = 10000)
	private String tag;

	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime lastEdit;

	private AnimeStatus status;
	private AnimeScore score;

	public ClientReview() {

	}

	public ClientReview(String id, Integer progress, String tag, LocalDateTime lastEdit,
			AnimeStatus status, AnimeScore score) {
		this.id = id;
		this.progress = progress;
		this.tag = tag;
		this.lastEdit = lastEdit;
		this.status = status;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
