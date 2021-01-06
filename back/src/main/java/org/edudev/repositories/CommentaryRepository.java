package org.edudev.repositories;

import org.edudev.models.Commentary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentaryRepository extends JpaRepository<Commentary, String> {
	
	@Query("FROM Commentary c WHERE c.to.id = :id")
	public Page<Commentary> findByPagedId(@Param("id")String id, PageRequest pageRequest);
}
