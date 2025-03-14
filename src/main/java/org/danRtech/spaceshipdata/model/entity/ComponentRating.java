package org.danRtech.spaceshipdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a rating and review given to a specific spaceship component.
 * This entity captures pilots' feedback, ratings, and optional comments
 * to evaluate the quality and performance of spaceship components.
 */
@Entity
@Data
@Table(name = "component_rating")
public class ComponentRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private SpaceshipComponent spaceshipComponent;

    @Column(name = "pilot_id")
    private Integer pilotId;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    protected ComponentRating() {
    }

    /**
     * Create fully initialised spaceship component rating
     * @param spaceshipComponent
     * @param pilotId
     * @param score
     * @param comment
     */
    public ComponentRating(SpaceshipComponent spaceshipComponent, Integer pilotId, Integer score, String comment) {
        this.spaceshipComponent = spaceshipComponent;
        this.pilotId = pilotId;
        this.score = score;
        this.comment = comment;
    }

    /**
     * Create a spaceship component rating (without comment)
     * @param spaceshipComponent
     * @param pilotId
     * @param score
     */
    public ComponentRating(SpaceshipComponent spaceshipComponent, Integer pilotId, Integer score) {
        this.spaceshipComponent = spaceshipComponent;
        this.pilotId = pilotId;
        this.score = score;
    }
}
