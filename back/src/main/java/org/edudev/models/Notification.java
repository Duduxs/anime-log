package org.edudev.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id = UUID.randomUUID().toString();
	
	private Client by;
	@ManyToOne
	private Client to;
		
	@NotBlank(message = "Título não pode estar vazio!")
	@Length(min = 5, max = 20, message = "Títulos entre 5 e 20 caracteres permitidos!")
	private String title;
	@NotBlank(message = "Mensagem não pode estar vazio!")
	@Length(min = 10, max = 50, message = "Mensagens entre 10 e 50 caracteres permitidas!")
	private String message;

	public Notification() {

	}

	public Notification(String id, String imgUrl, String sourceUser, String title, String message, Client by,
			Client to) {
		this.id = id;
		this.by = by;
		this.title = title;
		this.message = message;
		this.to = to;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Client getBy() {
		return by;
	}

	public void setBy(Client by) {
		this.by = by;
	}

	public Client getTo() {
		return to;
	}

	public void setTo(Client to) {
		this.to = to;
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
		Notification other = (Notification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
