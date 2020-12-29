package org.edudev.repositories;

import org.edudev.models.dtos.ClientFriendDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientFriendRepository extends JpaRepository<ClientFriendDTO, String> {

}
