package org.danRtech.spaceshipimages.repository;

import org.danRtech.spaceshipimages.model.projection.IdName;
import org.danRtech.spaceshipimages.model.entity.SpaceshipImage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpaceshipImageRepo extends MongoRepository<SpaceshipImage, String> {

        /**
         * Finds and returns SpaceshipImage by image file name.
         * @param name of the image file.
         * @return SpaceshipImage.
         */
        Optional<SpaceshipImage> findByFileName(String name);

        /**
         * Utilises projection interface. It searches for and returns only IDs and Names of the Images, avoiding the images' data.
         * @return List of objects of names and IDs.
         */
        List<IdName> findIdNameBy();
}
