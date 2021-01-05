package org.edudev.repositories;

import java.util.Optional;

import org.edudev.models.Anime;
import org.edudev.models.AnimeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeInfoRepository extends JpaRepository<AnimeInfo, String> {

	public Optional<AnimeInfo> findByEnglishTitle(String englishTitle);
}
