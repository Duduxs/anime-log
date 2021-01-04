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
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class FriendService {

	@Inject
	private FriendRepository repository;

	@Inject
	private ClientRepository clientRepository;

	public Friend save(String login, Friend friend) {
		//Melhorar...
		Client client = Optional.ofNullable(clientRepository.findByLogin(login)).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário remetente não encontrado!"));
		Client clientFriend = Optional.ofNullable(clientRepository.findByLogin(friend.getLogin())).orElseThrow(() -> WebError.returnError(Response.Status.NOT_FOUND, "Usuário destinatário não encontrado!"));
		
		repository.save(friend);
		client.getFriends().add(friend);
		clientRepository.save(client);

		Friend f = new Friend(client);
		
		repository.save(f);
		clientFriend.getFriends().add(f);
		clientRepository.save(clientFriend);
	
		return friend;
	}

	public Long count() {
		return repository.count();
	}
	
	public Page<Friend> findAllPaged(PageRequest pageRequest) {
		Page<Friend> friends = repository.findAll(pageRequest);
		
		if (friends.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return friends;
	}

	public Page<Friend> searchByLoginPaged(PageRequest pageRequest, String login) {
		Page<Friend> friends = repository.findByPagedLogin(login, pageRequest);
		
		if (friends.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findByPagedLogin(login, pageRequest);
	}

}
