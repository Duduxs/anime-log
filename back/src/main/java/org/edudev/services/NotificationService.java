package org.edudev.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.Notification;
import org.edudev.repositories.ClientRepository;
import org.edudev.repositories.NotificationRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class NotificationService {

	@Inject
	private NotificationRepository repository;
	
	@Inject
	private Validator validator;
	
	@Inject
	private ClientRepository clientRepository;
	
	public Page<Notification> findAllByPaged(PageRequest pageRequest) {
		if (repository.findAll(pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findAll(pageRequest);
	}

	public Notification save(Notification notification) {;
		validator.validateUserNotification(notification.getByLogin());

		Client client = clientRepository.findById(notification.getToLoginId()).orElseThrow( () -> WebError.returnError(Response.Status.NOT_FOUND, "Id do Usuário destinatário não encontrado!"));
		
		notification.setImgUrl(client.getImgUrl());
		repository.save(notification);
		
		client.getNotifications().add(notification);
		clientRepository.save(client);
		
		return notification; 
	}
}
