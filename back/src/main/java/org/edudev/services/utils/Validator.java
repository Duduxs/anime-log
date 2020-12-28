package org.edudev.services.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.edudev.models.dtos.ClientPostDTO;
import org.edudev.repositories.ClientRepository;

@ApplicationScoped
public class Validator {

	@Inject
	private ClientRepository repository;
	
	
	public void validateDTO(ClientPostDTO clientDTO) {
		
		if(clientDTO.getEmail() == "") 	WebError.sendError(Response.Status.BAD_REQUEST,"Email não pode estar vazio!");
		if(clientDTO.getLogin() == "") WebError.sendError(Response.Status.BAD_REQUEST,"Login não pode estar vazia!");
		if(clientDTO.getPassword() == "") WebError.sendError(Response.Status.BAD_REQUEST,"Senha não pode estar vazia!");

		if(!clientDTO.getEmail().contains("@")) WebError.sendError(Response.Status.BAD_REQUEST,"Email sem o @!");
		if(clientDTO.getLogin().length() < 4) WebError.sendError(Response.Status.BAD_REQUEST,"Login deve ter quatro caracteres ou mais!");
		if(clientDTO.getPassword().length() < 5) WebError.sendError(Response.Status.BAD_REQUEST,"Senha deve ter cinco caracteres ou mais!");
		
		if(repository.findByLogin(clientDTO.getLogin()) != null) WebError.sendError(Response.Status.CONFLICT,"Login já existe");
		if(repository.findByEmail(clientDTO.getEmail()) != null) WebError.sendError(Response.Status.CONFLICT,"Email já existe");
	}
}
