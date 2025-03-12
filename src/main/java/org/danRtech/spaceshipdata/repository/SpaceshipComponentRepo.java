package org.danRtech.spaceshipdata.repository;

import org.danRtech.spaceshipdata.model.entity.Spaceship;
import org.danRtech.spaceshipdata.model.entity.SpaceshipComponent;
import org.danRtech.spaceshipdata.model.enums.SpaceshipSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceshipComponentRepo extends JpaRepository<SpaceshipComponent, Integer> {
    List<SpaceshipComponent> findBySpaceshipSize(SpaceshipSize spaceshipSize);
    List<SpaceshipComponent> findBySpaceshipCode(String spaceshipCode);
}
