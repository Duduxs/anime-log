package org.edudev.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Notification;
import org.edudev.models.dtos.NotificationDTO;
import org.edudev.repositories.NotificationRepository;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class NotificationService {

	@Inject
	private NotificationRepository repository;

	public NotificationDTO save(Notification notification) {
		repository.save(notification);
		return new NotificationDTO(notification);
	}
	
	public Long count() {
		return repository.count();
	}
	
	public Page<NotificationDTO> findAllPaged(PageRequest pageRequest) {
		Page<Notification> notifications = repository.findAll(pageRequest);
		
		if (notifications.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return notifications.map(n -> new NotificationDTO(n));
	}
	
	public Page<NotificationDTO> findAllByUserIdPaged(PageRequest pageRequest, String id) {
		Page <Notification> notifications = repository.findByPagedId(id, pageRequest);
		
		if (notifications.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return notifications.map(n -> new NotificationDTO(n));
	}

	public void deleteById(String id) {
		if(!repository.findById(id).isPresent() || id.isBlank()) 
			WebError.sendError(Response.Status.NOT_FOUND, "Notificação não encontrada!");
		
		repository.deleteById(id);
	}
}
