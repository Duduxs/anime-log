package org.edudev.repositories;

import org.edudev.enums.AnimeStatus;
import org.edudev.models.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimeRepository extends JpaRepository<Anime, String>{
	
	@Query("FROM Anime a WHERE a.status = :status")
	public Page<Anime> findByPagedAnimeStatus(@Param("status")AnimeStatus status, PageRequest pageRequest);
	
	@Query("FROM Anime a WHERE a.client.id = :id AND LOWER(a.animeInfo.englishTitle) LIKE CONCAT('%',LOWER(:title),'%')")
	public Page<Anime> findByPagedEnglishTitle(@Param("id")String id, @Param("title")String title, PageRequest pageRequest);
		
}
