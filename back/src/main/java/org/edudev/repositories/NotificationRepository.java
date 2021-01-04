package org.edudev.repositories;

import org.edudev.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, String> {

	@Query("FROM Notification n WHERE n.to.id = :id")
	public Page<Notification> findByPagedId(@Param("id")String id, PageRequest pageRequest);

}
