package org.edudev.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.edudev.enums.Genre;

@Entity
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id = UUID.randomUUID().toString();
	private String name;
	private String local;
	private String imgUrl;

	private Genre genre;

	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String login;
	private String password;

	private LocalDateTime birthdate;
	private LocalDateTime lastTimeOnline;
	private LocalDateTime enterDate;

	@OneToMany(mappedBy = "toLoginId")
	private List<Notification> notifications = new ArrayList<>();

	public Client() {

	}

	public Client(String id, String name, Genre genre, String local, String email, String login, String password,
			LocalDateTime birthdate, LocalDateTime lastTimeOnline, LocalDateTime enterDate, String imgUrl) {
		this.id = id;
		this.name = name;
		this.imgUrl = imgUrl;
		this.genre = genre;
		this.local = local;
		this.email = email;
		this.login = login;
		this.password = password;
		this.birthdate = birthdate;
		this.lastTimeOnline = lastTimeOnline;
		this.enterDate = enterDate;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
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

	public LocalDateTime getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDateTime birthdate) {
		this.birthdate = birthdate;
	}

	public LocalDateTime getLastTimeOnline() {
		return lastTimeOnline;
	}

	public void setLastTimeOnline(LocalDateTime lastTimeOnline) {
		this.lastTimeOnline = lastTimeOnline;
	}

	public LocalDateTime getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(LocalDateTime enterDate) {
		this.enterDate = enterDate;
	}

	public List<Notification> getNotifications() {
		return notifications;
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
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
