package org.edudev.services.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.edudev.models.dtos.ClientPostDTO;
import org.edudev.repositories.ClientRepository;

@ApplicationScoped
public class Validator {

	@Inject
	private ClientRepository repository;
	
	
	public String validateDTO(ClientPostDTO clientDTO) {
		if(clientDTO.getEmail() == "") return "Email não pode estar vazia!";
		if(clientDTO.getLogin() == "") return "Login não pode estar vazia!";
		if(clientDTO.getPassword() == "") return "Senha não pode estar vazia!";

		if(!clientDTO.getEmail().contains("@")) return "Email sem o @!";
		if(clientDTO.getLogin().length() < 4) return "Login deve ter quatro caracteres ou mais!";
		if(clientDTO.getPassword().length() < 5) return "Senha deve ter cinco caracteres ou mais!";
		
		if(repository.findByLogin(clientDTO.getLogin()) != null) return "Login já existe";
		if(repository.findByEmail(clientDTO.getEmail()) != null) return "Email já existe";
		
		return "";
		
	}
	
}
