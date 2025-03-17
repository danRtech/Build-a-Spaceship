package org.danRtech.spaceshipdata.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.danRtech.spaceshipdata.model.entity.ComponentRating;

/**
 * Data transfer object for a Rating of a Spaceship Component.
 */
@Data
public class RatingDto {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer pilotId;

    public RatingDto() {
    }

    public RatingDto(Integer score, String comment, Integer pilotId) {
        this.score = score;
        this.comment = comment;
        this.pilotId = pilotId;
    }

    public RatingDto(ComponentRating entity) {
        this.score = entity.getScore();
        this.comment = entity.getComment();
        this.pilotId = entity.getPilotId();
    }
}
