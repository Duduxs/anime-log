package org.edudev.models.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.edudev.models.Client;
import org.edudev.models.Commentary;
import org.edudev.models.Friend;
import org.edudev.models.Notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {

	private String id = UUID.randomUUID().toString();
	private String email;
	private String login;
	private String password;
	private String local;
	private String imgUrl;
	
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime enterDate;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime lastTimeOnline;
	
	private Set<Notification> notifications = new HashSet<>();
	private List<Commentary> commentaries = new ArrayList<>();
	private Set<Friend> friends = new HashSet<>();

	public ClientDTO() {

	}

	public ClientDTO(String id, String email, String login, String password, LocalDateTime enterDate, LocalDateTime lastTimeOnline) {
		this.id = id;
		this.email = email;
		this.login = login;
		this.password = password;
		this.enterDate = enterDate;
		this.lastTimeOnline = lastTimeOnline;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public LocalDateTime getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(LocalDateTime enterDate) {
		this.enterDate = enterDate;
	}
	
	public LocalDateTime getLastTimeOnline() {
		return lastTimeOnline;
	}

	public void setLastTimeOnline(LocalDateTime lastTimeOnline) {
		this.lastTimeOnline = lastTimeOnline;
	}

	public Set<Notification> getNotifications() {
		return notifications;
	}
	
	public List<Commentary> getCommentaries() {
		return commentaries;
	}
	
	public Set<Friend> getFriends() {
		return friends;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((enterDate == null) ? 0 : enterDate.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		ClientDTO other = (ClientDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enterDate == null) {
			if (other.enterDate != null)
				return false;
		} else if (!enterDate.equals(other.enterDate))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
