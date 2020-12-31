package org.edudev.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.dtos.ClientFriendDTO;
import org.edudev.repositories.ClientFriendRepository;
import org.edudev.repositories.ClientRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;
import org.jboss.logging.Logger;

@ApplicationScoped
@Transactional
public class ClientFriendService {

	@Inject
	private ClientFriendRepository repository;
	
	@Inject
	private ClientRepository clientRepository;
	
	@Inject
	private Validator validator;
	
    private static final Logger LOG = Logger.getLogger(ClientFriendDTO.class);

	
	public ClientFriendDTO save(ClientFriendDTO clientFriendDTO) {	
		validator.validateUserFriend(clientFriendDTO.getByLogin());
		Client client = clientRepository.findById(clientFriendDTO.getToLoginId()).orElseThrow(
				() -> WebError.returnError(Response.Status.NOT_FOUND, "Login do destinatário não encontrado!"));
		
		LOG.info(client);
		clientFriendDTO = new ClientFriendDTO(client);
				
		repository.save(clientFriendDTO);
		
		client.getFriends().add(clientFriendDTO);
		clientRepository.save(client);
		
		return clientFriendDTO;
	}
}
