package org.danRtech.spaceshipdata.repository;

import org.danRtech.spaceshipdata.model.entity.ComponentRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ComponentRatingRepo extends JpaRepository<ComponentRating, Integer> {
    /**
     * Lookup all the ratings for a spaceship component.
     * @param componentId is the spaceship component identifier.
     * @return a list of any found ratings for the spaceship component.
     */
    List<ComponentRating> findByComponentId(Integer componentId);

    /**
     * Lookup the rating that was left by a pilot for a spaceship component.
     * @param componentId is a spaceship component identifier.
     * @param pilotId is a pilot identifier.
     * @return a rating left by the pilot for the spaceship component.
     */
    Optional<ComponentRating> findByComponentIdAndPilotId(Integer componentId, Integer pilotId);



}
