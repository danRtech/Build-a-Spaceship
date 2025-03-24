package org.danRtech.spaceshipimages.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class for spaceship images.
 * (MongoDB uses @Document annotation instead of the @Entity annotation).
 */
@Document(collection = "images")
@Data
public class SpaceshipImage {
    @Id
    private String id;
    private String fileName;
    private byte[] data;
}
