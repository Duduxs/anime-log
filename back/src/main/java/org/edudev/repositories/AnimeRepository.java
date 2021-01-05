package org.edudev.repositories;

import java.time.LocalDateTime;

import org.edudev.enums.AnimeStatus;
import org.edudev.models.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimeRepository extends JpaRepository<Anime, String>{
	
	@Query("FROM Anime a WHERE LOWER(a.englishTitle) LIKE CONCAT('%',LOWER(:title),'%')")
	public Page<Anime> findByPagedEnglishTitle(@Param("title")String title, PageRequest pageRequest);
	
	@Query("FROM Anime a WHERE a.releaseDate BETWEEN :startDate and :endDate")
	public Page<Anime> findByPagedLast20BetweenReleaseDates(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, PageRequest pageRequest);
	
	@Query("FROM Anime a WHERE a.status = :status")
	public Page<Anime> findByPagedAnimeStatus(@Param("status")AnimeStatus status, PageRequest pageRequest);
		
}
