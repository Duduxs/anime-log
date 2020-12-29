package org.edudev.repositories;

import org.edudev.models.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryRepository extends JpaRepository<Commentary, String> {
	
}
