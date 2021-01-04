package org.edudev.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Friend implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id = UUID.randomUUID().toString();
	
	private String imgUrl;
	@NotBlank(message = "Login não pode estar vazio!")
	private String login;
	
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime lastTimeOnline;
	private String local;

	
	public Friend() {
		
	}
	
	public Friend(String id, String imgUrl, String login, LocalDateTime lastTimeOnline, String local) {
		this.id = id;
		this.imgUrl = imgUrl;
		this.login = login;
		this.lastTimeOnline = lastTimeOnline;
		this.local = local;
	}
	
	public Friend(Client client) {
		this.imgUrl = client.getImgUrl();
		this.login = client.getLogin();
		this.lastTimeOnline = client.getLastTimeOnline();
		this.local = client.getLocal();
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public LocalDateTime getLastTimeOnline() {
		return lastTimeOnline;
	}

	public void setLastTimeOnline(LocalDateTime lastTimeOnline) {
		this.lastTimeOnline = lastTimeOnline;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
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
		Friend other = (Friend) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
