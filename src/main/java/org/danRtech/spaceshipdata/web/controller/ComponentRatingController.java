package org.danRtech.spaceshipdata.web.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.danRtech.spaceshipdata.model.entity.ComponentRating;
import org.danRtech.spaceshipdata.service.ComponentRatingService;
import org.danRtech.spaceshipdata.web.dto.RatingDto;
import org.danRtech.spaceshipdata.web.dto.ScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(path = "/component/{componentId}/ratings")
public class ComponentRatingController {
    private ComponentRatingService componentRatingService;

    @Autowired
    public ComponentRatingController(ComponentRatingService componentRatingService) {
        this.componentRatingService = componentRatingService;
    }

    /**
     * Creates new rating for a component.
     * @param componentId identification of the component the rating being created for.
     * @param ratingDto the rating details returned as RatingDto object.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComponentRating(
            @PathVariable (value = "componentId") int componentId, @RequestBody @Valid RatingDto ratingDto){
        log.info("POST /components/{}/ratings", componentId);
        componentRatingService.createNew(componentId,ratingDto.getPilotId(),ratingDto.getScore(), ratingDto.getComment());
    }

    /**
     * Returns all the ratings that were left for the spaceship component.
     *
     * @param componentId identification for the spaceship component.
     * @return List of all ratings for the spaceship component as RatingDto objects.
     */
    @GetMapping
    public List<RatingDto> getAllRatingsForComponent(@PathVariable(value = "componentId") int componentId){
        log.info("GET /components/{}/ratings", componentId);
        List<ComponentRating> componentRatings = componentRatingService.lookupRatingsByComponent(componentId);
        List<RatingDto> ratingDtoList = new ArrayList<>();
        for (ComponentRating rating : componentRatings) {
            ratingDtoList.add(new RatingDto(rating));
        }
        return ratingDtoList;
    }

    /**
     * Calculates and returns the average rating score for a spaceship component.
     *
     * @param componentId identification of the spaceship component.
     * @return the average rating as ScoreDto object.
     */
    @GetMapping("/average")
    public ScoreDto getAverageScore(@PathVariable(value = "componentId") int componentId){
        log.info("GET /components/{}/ratings/average", componentId);
       return new ScoreDto(componentRatingService.getAverageScore(componentId));
    }

    /**
     * Updates the spaceship component's rating.
     *
     * @param componentId identification of the spaceship component.
     * @param ratingDto object that holds the rating details.
     * @return updated rating details as RatingDto object.
     */
    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "componentId") int componentId,
                                   @RequestBody @Valid RatingDto ratingDto){
        log.info("PUT /components/{}/ratings", componentId);
        return new RatingDto(componentRatingService
                .update(componentId, ratingDto.getPilotId(), ratingDto.getScore(), ratingDto.getComment()));
    }

    /**
     * Deletes the rating that was left by the pilot for the spaceship component.
     *
     * @param componentId identification for the spaceship component.
     * @param pilotId identification of the spaceship pilot that left the rating which is being deleted now.
     */
    @DeleteMapping("/{pilotId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "componentId") int componentId,
                       @PathVariable(value = "pilotId") int pilotId){
        log.info("DELETE /components/{}/ratings/{}", componentId, pilotId);
        componentRatingService.delete(componentId, pilotId);
    }

    /**
     * Update some details of the rating that exists on the spaceship component.
     *
     * @param componentId the component that is being rated.
     * @param ratingDto the rating details to update.
     * @return updated rating details as RatingDto.
     */
    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "componentId") int componentId,
                       @RequestBody @Valid RatingDto ratingDto){
        log.info("PATCH /components/{}/ratings", componentId);
        Optional<Integer> score = Optional.ofNullable(ratingDto.getScore());
        Optional<String> comment = Optional.ofNullable(ratingDto.getComment());

        return new RatingDto(componentRatingService.updateSome(componentId, ratingDto.getPilotId(), score, comment));
    }

    /**
     * Multiple pilots rate one spaceship component with the same score at once.
     *
     * @param componentId the component that is being rated.
     * @param score the rating score for the component.
     * @param pilots the pilots that rated the component.
     */
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManyRatingsForOneComponent(@PathVariable(value = "componentId") int componentId,
                                      @RequestParam(value = "score") int score,
                                      @RequestBody List<Integer> pilots) {
        log.info("POST /components/{}/ratings/batch", componentId);
        componentRatingService.rateMany(componentId, score, pilots);
    }
}
