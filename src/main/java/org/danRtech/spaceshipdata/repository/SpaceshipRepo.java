package org.danRtech.spaceshipdata.repository;

import org.danRtech.spaceshipdata.model.entity.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceshipRepo extends JpaRepository<Spaceship, String> {
    Optional<Spaceship> findByName(String name);
}
