package org.edudev.repositories;

import org.edudev.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String>{

	public Client findByLogin(String login);
	public Client findByEmail(String email);
}
