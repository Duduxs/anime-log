package org.edudev.models.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClientFriendDTO {

	private String id = UUID.randomUUID().toString();
	private String name;
	private String imgUrl;
	private String local;

	private LocalDateTime lastTimeOnline;

	public ClientFriendDTO() {

	}

	public ClientFriendDTO(String id, String name, String imgUrl, String local, LocalDateTime lastTimeOnline) {
		this.id = id;
		this.name = name;
		this.imgUrl = imgUrl;
		this.local = local;
		this.lastTimeOnline = lastTimeOnline;
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
