package org.danRtech.spaceshipdata.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.danRtech.spaceshipdata.model.entity.ComponentRating;

/**
 * Data transfer object for a Rating of a Spaceship Component.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    @Min(value = 0, message = "The score cannot be below 0")
    @Max(value = 5, message = "The score cannot be more than 5")
    private Integer score;

    @Size(max = 255, message = "Comment cannot exceed 255 characters")
    private String comment;

    @NotNull(message = "Pilot ID cannot be null")
    private Integer pilotId;

    /**
     * This constructor converts ComponentRating entity into the RatingDto object
     * @param entity ComponentRating entity
     */
    public RatingDto(ComponentRating entity) {
        this.score = entity.getScore();
        this.comment = entity.getComment();
        this.pilotId = entity.getPilotId();
    }
}
