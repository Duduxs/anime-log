package org.edudev.services;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.Friend;
import org.edudev.repositories.ClientRepository;
import org.edudev.repositories.FriendRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ApplicationScoped
@Transactional
public class FriendService {

	@Inject
	private FriendRepository repository;

	@Inject
	private ClientRepository clientRepository;

	@Inject
	private Validator validator;

	private static final Logger LOG = Logger.getLogger(Friend.class);

	public Page<Friend> findAllByPaged(PageRequest pageRequest) {
		if (repository.findAll(pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findAll(pageRequest);
	}

	public Page<Friend> searchByLogin(String login) {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "login");

		if (repository.findByPagedLogin(login, pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findByPagedLogin(login, pageRequest);
	}

	public Long count() {
		return repository.count();
	}

	public Friend save(String login, Friend friend) {
		validator.validateFriend(friend.getLogin(), login);

		repository.save(friend);

		Client client = clientRepository.findByLogin(login);
		client.getFriends().add(friend);

		clientRepository.save(client);

		Friend f = new Friend(client);
		repository.save(f);

		Client clientFriend = clientRepository.findByLogin(friend.getLogin());
		clientFriend.getFriends().add(f);

		clientRepository.save(clientFriend);

		return friend;
	}

	public void deleteByLogin(String login) {
		//temporary
		Client client = Optional.ofNullable(clientRepository.findByLogin("Maria")).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário remetente não encontrado!"));
		Friend friend = Optional.ofNullable(repository.findByLogin(login)).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário destinatário não encontrado!")); 
	
		client.getFriends().remove(friend);
		clientRepository.saveAndFlush(client);
		repository.delete(friend);
		
		Client clientFriend = clientRepository.findByLogin("Eduardo");
		Friend f = repository.findByLogin("Maria");
		
		clientFriend.getFriends().remove(f);
		clientRepository.saveAndFlush(clientFriend);
		repository.delete(f);

	}
}
