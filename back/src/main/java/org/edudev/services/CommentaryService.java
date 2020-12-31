package org.edudev.services;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Client;
import org.edudev.models.Friend;
import org.edudev.models.Commentary;
import org.edudev.repositories.ClientRepository;
import org.edudev.repositories.CommentaryRepository;
import org.edudev.services.utils.Validator;
import org.edudev.services.utils.WebError;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class CommentaryService {

	@Inject
	private CommentaryRepository repository;
	
	private static final Logger LOG = Logger.getLogger(Commentary.class);
	
	@Inject
	private ClientRepository clientRepository;
	
	@Inject
	private Validator validator;
	
	public Page<Commentary> findAllByPaged(PageRequest pageRequest) {
		if (repository.findAll(pageRequest).isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return repository.findAll(pageRequest);
	}
	
	public Commentary save(Commentary commentaryDTO) {	
		validator.validateUserCommentary(commentaryDTO.getByLogin());
		Client client = clientRepository.findById(commentaryDTO.getToLoginId()).orElseThrow(
				() -> WebError.returnError(Response.Status.NOT_FOUND, "Id do destinatário não encontrado!"));
		
		commentaryDTO.setDatePost(LocalDateTime.now());
		repository.save(commentaryDTO);
		
		client.getCommentaries().add(commentaryDTO);
		clientRepository.save(client);
		
		return commentaryDTO;
	}
	
	public void update(Commentary commentaryDTO, String id) {
		Commentary commentaryUpdate = repository.findById(id).orElseThrow(
				() -> WebError.returnError(Response.Status.NOT_FOUND, "Id do destinatário não encontrado!"));
		commentaryUpdate.setDescription(commentaryDTO.getDescription());
	}
	
	public Long count() {
		return repository.count();
	}

	public void deleteById(String id) {
		if(!repository.findById(id).isPresent() || id.isBlank()) {
			WebError.sendError(Response.Status.NOT_FOUND, "Comentário não encontrado!");
		}
		repository.deleteById(id);
	
	}

	public void deleteAll() {
		repository.deleteAll();
	}
	
	
}
