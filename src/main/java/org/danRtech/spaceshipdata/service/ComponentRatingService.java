package org.danRtech.spaceshipdata.service;

import jakarta.validation.ConstraintViolationException;
import org.danRtech.spaceshipdata.model.entity.ComponentRating;
import org.danRtech.spaceshipdata.model.entity.SpaceshipComponent;
import org.danRtech.spaceshipdata.repository.ComponentRatingRepo;
import org.danRtech.spaceshipdata.repository.SpaceshipComponentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
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
        ComponentRating componentRating = new ComponentRating(verifyComponent(componentId), pilotId, score, comment);
        return componentRatingRepo.save(componentRating);
    }

    /**
     * Get spaceship component rating by rating id.
     *
     * @param id rating identifier
     * @return ComponentRating
     */
    public Optional<ComponentRating> lookupRatingById(int id) {
        return componentRatingRepo.findById(id);
    }

    /**
     * Get all ratings for all spaceship components.
     *
     * @return List of ComponentRatings
     */
    public List<ComponentRating> lookupAll() {
        return componentRatingRepo.findAll();
    }

    /**
     * Get a list of ratings for a spaceship component.
     *
     * @param componentId spaceship component identifier.
     * @return List of ratings available for the spaceship component.
     * @throws NoSuchElementException if no ratings found for the spaceship component.
     */
    public List<ComponentRating> lookupRatingsByComponent(int componentId) throws NoSuchElementException {
        return componentRatingRepo.findByComponentId(verifyComponent(componentId).getId());
    }

    /**
     * Update all the elements of a component rating.
     *
     * @param componentId  component identifier.
     * @param pilotId spaceship pilot identifier.
     * @param score   score of the component rating.
     * @param comment additional comment.
     * @return component rating object.
     * @throws NoSuchElementException if no component found.
     */
    public ComponentRating update(int componentId, Integer pilotId, Integer score, String comment)
            throws NoSuchElementException {
        ComponentRating rating = verifyComponentRating(componentId, pilotId);
        rating.setScore(score);
        rating.setComment(comment);
        return componentRatingRepo.save(rating);
    }

    /**
     * Update some of the elements of a component rating.
     *
     * @param componentId spaceship component identifier.
     * @param pilotId spaceship pilot identifier.
     * @param score score of the spaceship component rating.
     * @param comment comment of the spaceship component rating.
     * @return spaceship component rating object.
     * @throws NoSuchElementException if no Tour found.
     */
    public ComponentRating updateSome(int componentId, Integer pilotId, Optional<Integer> score, Optional<String> comment)
            throws NoSuchElementException {
        ComponentRating rating = verifyComponentRating(componentId, pilotId);
        score.ifPresent(s ->rating.setScore(s));
        comment.ifPresent(c -> rating.setComment(c));
        return componentRatingRepo.save(rating);
    }

    /**
     * Delete a spaceship component rating.
     *
     * @param componentId spaceship component identifier.
     * @param pilotId spaceship pilot identifier.
     * @throws NoSuchElementException if no spaceship component found.
     */
    public void delete(int componentId, Integer pilotId) throws NoSuchElementException {
        ComponentRating rating = verifyComponentRating(componentId, pilotId);
        componentRatingRepo.delete(rating);
    }

    /**
     * Get the average score of a spaceship component.
     *
     * @param componentId spaceship component identifier.
     * @return average score as a Double.
     * @throws NoSuchElementException if no ratings found for the component.
     */
    public Double getAverageScore(int componentId) throws NoSuchElementException {
        List<ComponentRating> ratings = componentRatingRepo.findByComponentId(verifyComponent(componentId).getId());

        // A traditional Java 7 approach:
        if (ratings.isEmpty()) {
            throw new NoSuchElementException("No ratings found for component ID: " + componentId);
        }

        int sum = 0;
        for (ComponentRating rating : ratings) {
            sum += rating.getScore();
        }

        return (double) sum / ratings.size();

        // Alternatively can be done as below using Stream API:
        // OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
        // return average.isPresent() ? average.getAsDouble() : null;
    }

    /**
     * A service that lets a group of pilots to rate one spaceship component with the same score at once
     * (a less likely scenario in a real application, but good enough to demo the Transactional functionality).
     *
     * @param componentId identification for the spaceship component.
     * @param score the rating score to be left by the group of pilots for the component.
     * @param pilotIDs the list of IDs for the group of pilots.
     */
    public void rateMany(int componentId, int score, List<Integer> pilotIDs){
        SpaceshipComponent component = verifyComponent(componentId);
        for(Integer pilotID : pilotIDs){
            if(componentRatingRepo.findByComponentIdAndPilotId(componentId, pilotID).isPresent()){
                String message = "Unable to create duplicate ratings. The component with ID " + componentId
                        + " is already rated by the pilot with ID " + pilotID + ".";
                throw new ConstraintViolationException(message, Collections.emptySet());
            }
            componentRatingRepo.save(new ComponentRating(component, pilotID, score));
        }
    }

    /**
     * Verify and return the spaceship component for the given spaceship component id.
     *
     * @param spaceshipComponentId identification for the spaceship component.
     * @return the found spaceship component.
     * @throws NoSuchElementException if no spaceship component found.
     */
    private SpaceshipComponent verifyComponent(int spaceshipComponentId) throws NoSuchElementException {
        return spaceshipComponentRepo.findById(spaceshipComponentId)
                .orElseThrow(() -> new NoSuchElementException("Spaceship component: " + spaceshipComponentId + " does not exist!"));
    }

    /**
     * Verify and return the component rating for a particular spaceship component and pilot
     *
     * @param componentId spaceship component identifier.
     * @param pilotId spaceship pilot identifier.
     * @return the found component rating.
     * @throws NoSuchElementException if unable to find the rating by component id and by pilot id.
     */
    public ComponentRating verifyComponentRating(int componentId, int pilotId) throws NoSuchElementException {
        return componentRatingRepo.findByComponentIdAndPilotId(componentId, pilotId)
                .orElseThrow(() -> new NoSuchElementException("Rating does not exist for component Id: "
                        + componentId + " and pilot id: " + pilotId));
    }
}
