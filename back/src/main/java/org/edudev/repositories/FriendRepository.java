package org.edudev.repositories;

import org.edudev.models.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRepository extends JpaRepository<Friend, String> {
	
	public Friend findByLogin(String login);
	
	@Query("FROM Friend f WHERE LOWER(f.login) LIKE CONCAT('%',LOWER(:login),'%')")
	public Page<Friend> findByPagedLogin(@Param("login")String login, PageRequest pageRequest);
}
