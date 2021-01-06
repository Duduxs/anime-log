package org.edudev.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Anime;
import org.edudev.models.Client;
import org.edudev.models.Friend;
import org.edudev.models.dtos.AnimeDTO;
import org.edudev.models.dtos.ClientDTO;
import org.edudev.models.dtos.CommentaryDTO;
import org.edudev.models.dtos.NotificationDTO;
import org.edudev.repositories.ClientRepository;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class ClientService {

	@Inject
	private ClientRepository repository;

	public ClientDTO save(ClientDTO clientDTO) {
		if (repository.findByLogin(clientDTO.getLogin()) != null)
			WebError.sendError(Response.Status.CONFLICT, "Login já existe");
		if (repository.findByEmail(clientDTO.getEmail()) != null)
			WebError.sendError(Response.Status.CONFLICT, "Email já existe");

		repository.save(fromDTO(clientDTO));
		return clientDTO;
	}

	public ClientDTO update(String id, Client entity) {
		Client client = repository.findById(id)
				.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Cliente não encontrado!"));
		
		ClientDTO dto = toDTOPut(client, entity);
		repository.save(client);
		return dto;
	}

	public ClientDTO login(String login, String password) {
		Client client = repository.findByLoginAndPassword(login, password)
				.orElseThrow(() -> WebError.returnError(Response.Status.UNAUTHORIZED, ""));
		client.setLastTimeOnline(LocalDateTime.now());
		client.setOnline(true);
		return toDTO(client);
	}

	public Long count() {
		return repository.count();
	}

	public ClientDTO findById(String id) {
		Optional<Client> client = repository.findById(id);
		ClientDTO clientDTO = toDTO(
				client.orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Cliente não encontrado!")));
		return clientDTO;
	}

	public Page<ClientDTO> findAllByPaged(PageRequest pageRequest) {
		if (repository.findAll(pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findAll(pageRequest).map(this::toDTO);
	}

	public List<ClientDTO> findLast10Online() {
		if (repository.findFirst10ByOnlineOrderByLoginDesc(true).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findFirst10ByOnlineOrderByLoginDesc(true).stream().map(this::toDTO)
				.collect(Collectors.toList());
	}

	public Page<ClientDTO> searchByLoginPaged(PageRequest pageRequest, String login) {
		if (repository.findByPagedLogin(login, pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findByPagedLogin(login, pageRequest).map(this::toDTO);
	}

	public Client fromDTO(ClientDTO clientDTO) {
		Client c = new Client();

		c.setEmail(clientDTO.getEmail());
		c.setLogin(clientDTO.getLogin());
		c.setPassword(clientDTO.getPassword());
		c.setEnterDate(LocalDateTime.now());
		c.setLastTimeOnline(LocalDateTime.now());

		clientDTO.setId(c.getId());
		clientDTO.setEnterDate(LocalDateTime.now());
		clientDTO.setLastTimeOnline(LocalDateTime.now());

		return c;
	}
	
	public ClientDTO toDTOPut(Client client,  Client entity) {
		client.setName(entity.getName());
		client.setLocal(entity.getLocal());
		client.setAbout(entity.getAbout());
		client.setImgUrl(entity.getImgUrl());
		client.setGenre(entity.getGenre());
		client.setBirthDate(entity.getBirthDate());
		return new ClientDTO(client.getId(), client.getName(), client.getLocal(), client.getAbout(), client.getImgUrl(), client.getEmail(), null, null, null, client.getBirthDate(), null,null);
	}

	public ClientDTO toDTO(Client client) {

		List<NotificationDTO> notificationsDTO = new ArrayList<>();
		client.getNotifications().forEach(n -> notificationsDTO.add(new NotificationDTO(n)));

		List<CommentaryDTO> commentariesDTO = new ArrayList<>();
		client.getCommentaries().forEach(c -> commentariesDTO.add(new CommentaryDTO(c)));

		ClientDTO clientDTO = new ClientDTO(client.getId(), client.getName(), client.getLocal(), client.getAbout(),
				client.getImgUrl(), client.getEmail(), client.getLogin(), null, client.getOnline(), client.getBirthDate(),
				client.getEnterDate(), client.getLastTimeOnline());

		for (NotificationDTO not : notificationsDTO)
			clientDTO.getNotifications().add(not);

		for (CommentaryDTO com : commentariesDTO)
			clientDTO.getCommentaries().add(com);

		for (Friend fri : client.getFriends())
			clientDTO.getFriends().add(fri);

		for (Anime ani : client.getAnimes())
			clientDTO.getAnimes().add(new AnimeDTO(ani));

		return clientDTO;
	}

}
