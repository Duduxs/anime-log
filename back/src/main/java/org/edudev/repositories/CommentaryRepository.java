package org.edudev.repositories;

import org.edudev.models.dtos.CommentaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryRepository extends JpaRepository<CommentaryDTO, String> {
	
}
