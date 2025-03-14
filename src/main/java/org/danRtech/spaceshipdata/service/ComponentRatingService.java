package org.danRtech.spaceshipdata.service;

import org.danRtech.spaceshipdata.model.entity.ComponentRating;
import org.danRtech.spaceshipdata.model.entity.SpaceshipComponent;
import org.danRtech.spaceshipdata.repository.ComponentRatingRepo;
import org.danRtech.spaceshipdata.repository.SpaceshipComponentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ComponentRatingService {

    private ComponentRatingRepo componentRatingRepo;
    private SpaceshipComponentRepo spaceshipComponentRepo;

    @Autowired
    public ComponentRatingService(ComponentRatingRepo componentRatingRepo, SpaceshipComponentRepo spaceshipComponentRepo) {
        this.componentRatingRepo = componentRatingRepo;
        this.spaceshipComponentRepo = spaceshipComponentRepo;
    }

    /**
     * Create new rating for a spaceship component.
     * @param componentId the component that is being rated.
     * @param pilotId the identification of  the pilot that left the rating.
     * @param score the rating score left by a pilot.
     * @param comment the comment that that was left while rating the spaceship component.
     * @return the new rating.
     */
    public ComponentRating createNew(int componentId, Integer pilotId, Integer score, String comment){
        ComponentRating componentRating = new ComponentRating(verifyTour(componentId), pilotId, score, comment);
        return componentRatingRepo.save(componentRating);
    }

    /**
     * Verify and return the spaceship component given a spaceship component id.
     *
     * @param spaceshipComponentId identification for the spaceship component.
     * @return the found spaceship component.
     * @throws NoSuchElementException if no spaceship component found.
     */
    private SpaceshipComponent verifyTour(int spaceshipComponentId) throws NoSuchElementException {
        return spaceshipComponentRepo.findById(spaceshipComponentId)
                .orElseThrow(() -> new NoSuchElementException("Spaceship component does not exist " + spaceshipComponentId));
    }
}
