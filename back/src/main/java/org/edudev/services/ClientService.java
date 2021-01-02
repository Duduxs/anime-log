package org.edudev.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Anime;
import org.edudev.models.Client;
import org.edudev.models.Commentary;
import org.edudev.models.Friend;
import org.edudev.models.Notification;
import org.edudev.models.dtos.AnimeDTO;
import org.edudev.models.dtos.ClientDTO;
import org.edudev.repositories.ClientRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ApplicationScoped
@Transactional
public class ClientService {

	@Inject
	private ClientRepository repository;

	private static final Logger LOG = Logger.getLogger(Client.class);

	@Inject
	private Validator validator;

	public ClientDTO findById(String id) {
		Optional<Client> client = repository.findById(id);
		ClientDTO clientDTO = toDTO(
				client.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Cliente não encontrado!")));
		return clientDTO;
	}

	public Long count() {
		return repository.count();
	}

	public Page<ClientDTO> findAllByPaged(PageRequest pageRequest) {
		if (repository.findAll(pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findAll(pageRequest).map(this::toDTO);
	}

	public List<ClientDTO> findLast10UsersOnline() {
		if (repository.findFirst10ByOnlineOrderByLoginDesc(true).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findFirst10ByOnlineOrderByLoginDesc(true).stream().map(this::toDTO)
				.collect(Collectors.toList());
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
		c.setLastTimeOnline(LocalDateTime.now());
		c.setOnline(true);

		clientDTO.setId(c.getId());
		clientDTO.setEnterDate(LocalDateTime.now());
		clientDTO.setLastTimeOnline(LocalDateTime.now());
		return c;
	}

	public ClientDTO toDTO(Client client) {

		ClientDTO clientDTO = new ClientDTO(client.getId(), client.getEmail(), client.getLogin(), true,
				client.getPassword(), client.getEnterDate(), client.getLastTimeOnline());

		for (Notification not : client.getNotifications())
			clientDTO.getNotifications().add(not);

		for (Commentary com : client.getCommentaries())
			clientDTO.getCommentaries().add(com);

		for (Friend fri : client.getFriends())
			clientDTO.getFriends().add(fri);

		for (Anime ani : client.getAnimes())
			clientDTO.getAnimes().add(new AnimeDTO(ani, ""));

		return clientDTO;
	}

}
