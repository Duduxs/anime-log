package org.edudev.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.PastOrPresent;

import org.edudev.enums.Genre;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id = UUID.randomUUID().toString();
	
	private String name;
	private String local;
	private String about;
	private String imgUrl;

	private Genre genre;

	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String login;
	private String password;
	private Boolean online;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	@PastOrPresent
	private LocalDateTime birthDate;	
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	@PastOrPresent
	private LocalDateTime lastTimeOnline;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	@PastOrPresent
	private LocalDateTime enterDate;

	@OneToMany(mappedBy = "to", cascade = CascadeType.REMOVE)
	private Set<Notification> notifications = new HashSet<>();

	@OneToMany(mappedBy = "to", cascade = CascadeType.REMOVE)
	private List<Commentary> commentaries = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(name = "client_friend_join", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "clientFriend_id"))
	private Set<Friend> friends = new HashSet<>();
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
	private Set<Anime> animes = new HashSet<>();

	public Client() {

	}

	public Client(String id, String name, String local, String about, String imgUrl, Genre genre, String email,
			String login, String password, Boolean online, LocalDateTime birthDate, LocalDateTime lastTimeOnline,
			LocalDateTime enterDate) {
		this.id = id;
		this.name = name;
		this.local = local;
		this.about = about;
		this.imgUrl = imgUrl;
		this.genre = genre;
		this.email = email;
		this.login = login;
		this.password = password;
		this.online = online;
		this.birthDate = birthDate;
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

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
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

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
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

	public Set<Notification> getNotifications() {
		return notifications;
	}

	public List<Commentary> getCommentaries() {
		return commentaries;
	}

	public Set<Friend> getFriends() {
		return friends;
	}
	
	public Set<Anime> getAnimes() {
		return animes;
	}


	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", local=" + local + ", imgUrl=" + imgUrl + ", genre=" + genre
				+ ", email=" + email + ", login=" + login + ", password=" + password + ", birthDate=" + birthDate
				+ ", lastTimeOnline=" + lastTimeOnline + ", enterDate=" + enterDate + ", notifications=" + notifications
				+ ", commentaries=" + commentaries + ", friends=" + friends + "]";
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
