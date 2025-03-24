package org.danRtech.spaceshipimages.model.projection;

/**
 * Interface-based projection is used here to get only the IDs and File Names of the images,
 * omitting the actual images' data (which can be huge). An alternative way would be to use a DTO
 * to filter out the mage data. DTOs approach gives more control, but is slower and takes up more memory.
 */
public interface IdName {
    String getId();
    String getFileName();
}
