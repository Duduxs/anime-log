package org.edudev.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

@Entity
public class Commentary implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id = UUID.randomUUID().toString();
	
	@NotBlank(message = "byLogin não pode estar vazio!")
	private String byLogin;
	@NotBlank(message = "toLoginId não pode estar vazio!")
	private String toLoginId;
	
	private String imgUrl;

	@Length(min = 1, max = 12000, message = "Comentários entre 1 e 12000 caracteres permitidos!")
	@NotBlank(message = "Description não pode estar vazia!")
	private String description;
	@Column(nullable = true)
	@PastOrPresent
	private LocalDateTime datePost;
	
	public Commentary() {

	}

	public Commentary(String id, String imgUrl, String byLogin, String description, LocalDateTime datePost) {
		this.id = id;
		this.imgUrl = imgUrl;
		this.byLogin = byLogin;
		this.description = description;
		this.datePost = datePost;
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

	public String getByLogin() {
		return byLogin;
	}

	public void setByLogin(String byLogin) {
		this.byLogin = byLogin;
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
	
	public String getToLoginId() {
		return toLoginId;
	}

	public void setToLoginId(String toLoginId) {
		this.toLoginId = toLoginId;
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
		Commentary other = (Commentary) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
