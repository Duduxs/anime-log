package org.edudev.models.dtos;

import java.time.LocalDateTime;

import javax.validation.constraints.PastOrPresent;

import org.edudev.models.Commentary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentaryDTO {

	private String id;

	private ClientDTO by;
	private String description;
	
	@PastOrPresent
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime datePost;

	public CommentaryDTO() {

	}

	public CommentaryDTO(String id, ClientDTO by, String imgUrl, String description, LocalDateTime datePost) {
		this.id = id;
		this.by = by;
		this.description = description;
		this.datePost = datePost;
	}
	
	public CommentaryDTO(Commentary commentary) {
		this.by = new ClientDTO();

		this.setId(commentary.getId());
		
		this.by.setId(commentary.getBy().getId());
		this.by.setImgUrl(commentary.getBy().getImgUrl());
		this.by.setLogin(commentary.getBy().getLogin());

		this.setDescription(commentary.getDescription());
		this.setDatePost(commentary.getDatePost());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ClientDTO getBy() {
		return by;
	}

	public void setBy(ClientDTO by) {
		this.by = by;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDatePost() {
		return datePost;
	}

	public void setDatePost(LocalDateTime datePost) {
		this.datePost = datePost;
	}

}
