package org.adoxx.socialmedia.repositories;

import org.adoxx.socialmedia.models.entities.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PinRepository extends JpaRepository<Pin, String> {
    Optional<Pin> findByPinId(String pinId);
}
