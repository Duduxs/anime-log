package org.edudev.repositories;

import java.util.List;
import java.util.Optional;

import org.edudev.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, String>{

	public Client findByLogin(String login);
	public Client findByEmail(String email);
	
	@Query("FROM Client c WHERE LOWER(c.login) LIKE CONCAT('%',LOWER(:login),'%')")
	public Page<Client> findByPagedLogin(@Param("login")String login, PageRequest pageRequest);

	public List<Client> findFirst10ByOnlineOrderByLoginDesc(Boolean online);
	public Optional<Client> findByLoginAndPassword(String login, String password);
}
