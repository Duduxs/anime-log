package org.edudev.services;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.dtos.ClientPostDTO;
import org.edudev.repositories.ClientRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ApplicationScoped
public class ClientService {

	@Inject
	private ClientRepository repository;

	@Inject
	private Validator validator;

	@Transactional
	public Client findById(String id) {
		Optional<Client> client = repository.findById(id);
		return client.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Client not found!"));
	}

	@Transactional
	public Page<Client> findAllByPaged(PageRequest pageRequest) {
		if (repository.findAll(pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findAll(pageRequest);
	}

	@Transactional
	public Page<Client> searchByLogin(String login) {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "login");

		if (repository.findByPagedLogin(login, pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findByPagedLogin(login, pageRequest);
	}

	@Transactional
	public Client login(String login, String password) {
		Optional<Client> client = repository.findByLoginAndPassword(login, password);
		return client.orElseThrow(() -> WebError.returnError(Response.Status.UNAUTHORIZED, ""));
	}

	@Transactional
	public ClientPostDTO save(ClientPostDTO clientDTO) {
		validator.validateDTO(clientDTO);
		repository.save(fromDTO(clientDTO));
		return clientDTO;
	}

	public Client fromDTO(ClientPostDTO clientDTO) {
		Client c = new Client();

		c.setEmail(clientDTO.getEmail());
		c.setLogin(clientDTO.getLogin());
		c.setPassword(clientDTO.getPassword());
		c.setEnterDate(LocalDateTime.now());

		clientDTO.setId(c.getId());
		clientDTO.setEnterDate(LocalDateTime.now());

		return c;
	}
}
