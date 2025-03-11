package org.danRtech.spaceshipdata.repository;

import org.danRtech.spaceshipdata.model.entity.SpaceshipComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceshipComponentRepo extends JpaRepository<SpaceshipComponent, Integer> {

}
