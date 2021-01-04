package org.edudev.services;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.edudev.models.Commentary;
import org.edudev.models.dtos.CommentaryDTO;
import org.edudev.repositories.CommentaryRepository;
import org.edudev.services.utils.WebError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ApplicationScoped
@Transactional
public class CommentaryService {

	@Inject
	private CommentaryRepository repository;

	public CommentaryDTO save(Commentary commentary) {
		commentary.setDatePost(LocalDateTime.now());
		
		repository.save(commentary);
		return new CommentaryDTO(commentary);
	}
	
	public CommentaryDTO update(Commentary commentary, String id) {
		Commentary commentaryUpdate = repository.findById(id).orElseThrow(
				() -> WebError.returnError(Response.Status.NOT_FOUND, "Comentário não encontrado!"));
		
		commentaryUpdate.setDescription(commentary.getDescription());
		repository.save(commentaryUpdate);
		
		return new CommentaryDTO(commentaryUpdate);
	}
	
	public Long count() {
		return repository.count();
	}
	
	public Page<CommentaryDTO> findAllByPaged(PageRequest pageRequest) {
		Page<Commentary> commentaries = repository.findAll(pageRequest);
		
		if (commentaries.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return commentaries.map(c -> new CommentaryDTO(c));
	}
	
	public Page<CommentaryDTO> findAllByUserIdPaged(PageRequest pageRequest, String id) {
		Page <Commentary> commentaries = repository.findByPagedId(id, pageRequest);
		
		if (commentaries.isEmpty())
			WebError.sendError(Response.Status.NO_CONTENT, "");

		return commentaries.map(c -> new CommentaryDTO(c));
	}

	public void deleteById(String id) {
		if (!repository.findById(id).isPresent())
			WebError.sendError(Response.Status.NOT_FOUND, "Comentário não encontrado!");

		repository.deleteById(id);
	}

}
