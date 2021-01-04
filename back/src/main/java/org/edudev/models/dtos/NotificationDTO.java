package org.edudev.models.dtos;

import org.edudev.models.Notification;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTO {

	private String id;

	private ClientDTO by;

	private String title;
	private String message;

	public NotificationDTO() {

	}

	public NotificationDTO(String id, ClientDTO by,  String title, String message) {
		this.id = id;
		this.by = by;
		this.title = title;
		this.message = message;
	}
	
	public NotificationDTO(Notification notification) {
		this.by = new ClientDTO();

		this.setId(notification.getId());
		
		this.by.setId(notification.getBy().getId());
		this.by.setImgUrl(notification.getBy().getImgUrl());
		this.by.setLogin(notification.getBy().getLogin());

		this.setTitle(notification.getTitle());
		this.setMessage(notification.getMessage());
		
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
