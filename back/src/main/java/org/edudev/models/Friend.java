package org.edudev.models.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.edudev.models.Client;

@Entity
@Table(name =  "clientFriend")
public class ClientFriendDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id = UUID.randomUUID().toString();
	private String byLogin;
	private String toLoginId;
	private String imgUrl;
	private String local;

	private LocalDateTime lastTimeOnline;

	public ClientFriendDTO() {

	}

	public ClientFriendDTO(String id, String byLogin, String imgUrl, String local, LocalDateTime lastTimeOnline) {
		this.id = id;
		this.byLogin = byLogin;
		this.imgUrl = imgUrl;
		this.local = local;
		this.lastTimeOnline = lastTimeOnline;
	}
	
	public ClientFriendDTO(Client client) {
		this.id = client.getId();
		this.byLogin = client.getLogin();
		this.imgUrl = client.getImgUrl();
		this.local = client.getLocal();
		this.lastTimeOnline = client.getLastTimeOnline();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getByLogin() {
		return byLogin;
	}

	public void setByLogin(String byLogin) {
		this.byLogin = byLogin;
	}
	
	public String getToLoginId() {
		return toLoginId;
	}

	public void setToLoginId(String toLoginId) {
		this.toLoginId = toLoginId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public LocalDateTime getLastTimeOnline() {
		return lastTimeOnline;
	}

	public void setLastTimeOnline(LocalDateTime lastTimeOnline) {
		this.lastTimeOnline = lastTimeOnline;
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
		ClientFriendDTO other = (ClientFriendDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
