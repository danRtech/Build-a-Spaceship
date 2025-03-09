package org.danRtech.spaceshipdata.repository;

import org.danRtech.spaceshipdata.model.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceshipRepo extends JpaRepository<Spaceship, String> {

}
