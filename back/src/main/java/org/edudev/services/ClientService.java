package org.edudev.services;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.dtos.ClientPostDTO;
import org.edudev.repositories.ClientRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;

@ApplicationScoped
public class ClientService {

	@Inject
	private ClientRepository repository;

	@Inject
	private Validator validator;

	@Transactional
	public ClientPostDTO save(ClientPostDTO clientDTO) {
		if (validator.validateDTO(clientDTO) != "") WebError.sendError(Response.Status.BAD_REQUEST, validator.validateDTO(clientDTO));

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
