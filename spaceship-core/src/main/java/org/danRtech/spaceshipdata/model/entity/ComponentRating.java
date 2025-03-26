package org.danRtech.spaceshipdata.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a rating and review given to a specific spaceship component.
 * This entity captures pilots' feedback, ratings, and optional comments
 * to evaluate the quality and performance of spaceship components.
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "component_rating")
public class ComponentRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private SpaceshipComponent component;

    @Column(name = "pilot_id")
    private Integer pilotId;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    /**
     * Create fully initialised spaceship component rating
     * @param component
     * @param pilotId
     * @param score
     * @param comment
     */
    public ComponentRating(SpaceshipComponent component, Integer pilotId, Integer score, String comment) {
        this.component = component;
        this.pilotId = pilotId;
        this.score = score;
        this.comment = comment;
    }

    /**
     * Create a spaceship component rating (without comment)
     * @param component
     * @param pilotId
     * @param score
     */
    public ComponentRating(SpaceshipComponent component, Integer pilotId, Integer score) {
        this.component = component;
        this.pilotId = pilotId;
        this.score = score;
    }
}
