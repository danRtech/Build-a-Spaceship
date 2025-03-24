package org.danRtech.spaceshipimages.service;

import lombok.NoArgsConstructor;
import org.danRtech.spaceshipimages.model.IdName;
import org.danRtech.spaceshipimages.model.SpaceshipImage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class SpaceshipImageService {

    public SpaceshipImage saveImage(SpaceshipImage image) {
        return null;
    }

    public Optional<SpaceshipImage> getImage(String id) {
        return null;
    }

    public Optional<SpaceshipImage> findByName(String name) {
        return null;
    }

    public List<IdName> findIdNames() {
        return null;
    }
}
