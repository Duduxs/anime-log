package org.edudev.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.edudev.models.AnimeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimeInfoRepository extends JpaRepository<AnimeInfo, String> {

	public Optional<AnimeInfo> findByEnglishTitle(String englishTitle);
	
	@Query("FROM AnimeInfo ai WHERE ai.releaseDate BETWEEN :startDate and :endDate")
	public Page<AnimeInfo> findByPagedLast20BetweenReleaseDates(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, PageRequest pageRequest);	
	
	@Query("FROM AnimeInfo ai WHERE LOWER(ai.englishTitle) LIKE CONCAT('%',LOWER(:title),'%')")
	public Page<AnimeInfo> findByPagedEnglishTitle(@Param("title")String title, PageRequest pageRequest);
}
