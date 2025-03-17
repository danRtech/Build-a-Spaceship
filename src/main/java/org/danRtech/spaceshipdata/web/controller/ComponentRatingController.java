package org.danRtech.spaceshipdata.web.controller;

import jakarta.validation.Valid;
import org.danRtech.spaceshipdata.model.entity.ComponentRating;
import org.danRtech.spaceshipdata.service.ComponentRatingService;
import org.danRtech.spaceshipdata.web.dto.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
        return componentRatings.stream().map(RatingDto::new).toList();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String return404(NoSuchElementException exception){
        return exception.getMessage();
    }
}
