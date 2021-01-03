package org.edudev.services;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.Notification;
import org.edudev.repositories.ClientRepository;
import org.edudev.repositories.NotificationRepository;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class NotificationService {

	@Inject
	private NotificationRepository repository;

	@Inject
	private ClientRepository clientRepository;

	public Notification save(Notification notification) {
		Optional.ofNullable(clientRepository.findByLogin(notification.getByLogin())).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário remetente não encontrado!"));;

		Client client = clientRepository.findById(notification.getToLoginId()).orElseThrow(
				() -> WebError.returnError(Response.Status.NOT_FOUND, "Id do destinatário não encontrado!"));

		notification.setImgUrl(client.getImgUrl());
		repository.save(notification);

		client.getNotifications().add(notification);
		clientRepository.save(client);

		return notification;
	}
	
	public Long count() {
		return repository.count();
	}
	
	public Page<Notification> findAllPaged(PageRequest pageRequest) {
		if (repository.findAll(pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findAll(pageRequest);
	}

	public void deleteById(String id) {
		if(!repository.findById(id).isPresent() || id.isBlank()) 
			WebError.sendError(Response.Status.NOT_FOUND, "Notificação não encontrada!");
		
		repository.deleteById(id);
	}

	public void deleteAll() {
		repository.deleteAll();
	}
}
