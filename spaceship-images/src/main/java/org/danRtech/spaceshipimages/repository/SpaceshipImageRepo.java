package org.danRtech.spaceshipimages.repository;

import org.danRtech.spaceshipimages.model.IdName;
import org.danRtech.spaceshipimages.model.SpaceshipImage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpaceshipImageRepo extends MongoRepository<SpaceshipImage, String> {
        Optional<SpaceshipImage> findByFileName(String name);
        List<IdName> findIdNameBy();
}
