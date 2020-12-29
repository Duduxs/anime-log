package org.edudev.services;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.Notification;
import org.edudev.models.dtos.ClientDTO;
import org.edudev.models.dtos.CommentaryDTO;
import org.edudev.repositories.ClientRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ApplicationScoped
@Transactional
public class ClientService {

	@Inject
	private ClientRepository repository;

	@Inject
	private Validator validator;

	public ClientDTO findById(String id) {
		Optional<Client> client = repository.findById(id);
		ClientDTO clientDTO = toDTO(client.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Cliente não encontrado!")));
		return clientDTO;
	}

	public Page<ClientDTO> findAllByPaged(PageRequest pageRequest) {
		if (repository.findAll(pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findAll(pageRequest).map(this::toDTO);
	}

	public Page<ClientDTO> searchByLogin(String login) {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "login");

		if (repository.findByPagedLogin(login, pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findByPagedLogin(login, pageRequest).map(this::toDTO);
	}

	public ClientDTO login(String login, String password) {
		Optional<Client> client = repository.findByLoginAndPassword(login, password);
		return toDTO(client.orElseThrow(() -> WebError.returnError(Response.Status.UNAUTHORIZED, "")));
	}

	public ClientDTO save(ClientDTO clientDTO) {
		validator.validateDTO(clientDTO);
		repository.save(fromDTO(clientDTO));
		return clientDTO;
	}

	public Client fromDTO(ClientDTO clientDTO) {
		Client c = new Client();

		c.setEmail(clientDTO.getEmail());
		c.setLogin(clientDTO.getLogin());
		c.setPassword(clientDTO.getPassword());
		c.setEnterDate(LocalDateTime.now());

		clientDTO.setId(c.getId());
		clientDTO.setEnterDate(LocalDateTime.now());
		
		return c;
	}
	
	public ClientDTO toDTO(Client client) {
		ClientDTO clientDTO = new ClientDTO(client.getId(), client.getEmail(), client.getLogin(), client.getPassword(), client.getEnterDate());
		
		for(Notification not : client.getNotifications()) 
			clientDTO.getNotifications().add(not);
		
		for(CommentaryDTO com : client.getCommantaries()) 
			clientDTO.getCommentaries().add(com);
		
		return clientDTO;
	}
}
