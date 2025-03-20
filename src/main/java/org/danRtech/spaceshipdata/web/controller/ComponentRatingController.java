package org.danRtech.spaceshipdata.web.controller;

import jakarta.validation.Valid;
import org.danRtech.spaceshipdata.model.entity.ComponentRating;
import org.danRtech.spaceshipdata.service.ComponentRatingService;
import org.danRtech.spaceshipdata.web.dto.RatingDto;
import org.danRtech.spaceshipdata.web.dto.ScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/component/{componentId}/ratings")
public class ComponentRatingController {
    private ComponentRatingService componentRatingService;

    @Autowired
    public ComponentRatingController(ComponentRatingService componentRatingService) {
        this.componentRatingService = componentRatingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComponentRating(
            @PathVariable (value = "componentId") int componentId, @RequestBody @Valid RatingDto ratingDto){
        componentRatingService.createNew(componentId,ratingDto.getPilotId(),ratingDto.getScore(), ratingDto.getComment());
    }

    @GetMapping
    public List<RatingDto> getAllRatingsForSpaceshipComponent(@PathVariable(value = "componentId") int componentId){
        List<ComponentRating> componentRatings = componentRatingService.lookupRatingsByComponent(componentId);
        List<RatingDto> ratingDtoList = new ArrayList<>();

        for (ComponentRating rating : componentRatings) {
            ratingDtoList.add(new RatingDto(rating));
        }
        return ratingDtoList;
    }

    @GetMapping("/average")
    public ScoreDto getAverage(@PathVariable(value = "componentId") int componentId){
       return new ScoreDto(componentRatingService.getAverageScore(componentId));
    }

    @PutMapping
    public RatingDto updataWithPut(@PathVariable(value = "componentId") int componentId,
                                   @RequestBody @Valid RatingDto ratingDto){
        return new RatingDto(componentRatingService
                .update(componentId, ratingDto.getPilotId(), ratingDto.getScore(), ratingDto.getComment()));
    }

    @DeleteMapping("/{pilotId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "componentId") int componentId,
                       @PathVariable(value = "pilotId") int pilotId){
        componentRatingService.delete(componentId, pilotId);
    }

    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "componentId") int componentId,
                       @RequestBody @Valid RatingDto ratingDto){

        Optional<Integer> score = Optional.ofNullable(ratingDto.getScore());
        Optional<String> comment = Optional.ofNullable(ratingDto.getComment());

        return new RatingDto(componentRatingService.updateSome(componentId, ratingDto.getPilotId(), score, comment));
    }

    /**
     * Group of pilots rates one spaceship component with the same score at once.
     *
     * @param componentId
     * @param score
     * @param pilots
     */
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public void createManyRatingsForOneComponent(@PathVariable(value = "componentId") int componentId,
                                      @RequestParam(value = "score") int score,
                                      @RequestBody List<Integer> pilots) {
        componentRatingService.rateMany(componentId, score, pilots);
    }
}
