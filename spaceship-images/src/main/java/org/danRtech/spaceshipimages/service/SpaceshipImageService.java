package org.danRtech.spaceshipimages.service;

import lombok.NoArgsConstructor;
import org.danRtech.spaceshipimages.model.projection.IdName;
import org.danRtech.spaceshipimages.model.entity.SpaceshipImage;
import org.danRtech.spaceshipimages.repository.SpaceshipImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class SpaceshipImageService {

    private SpaceshipImageRepo imageRepo;

    @Autowired
    public SpaceshipImageService(SpaceshipImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    /**
     * Save Spaceship Image into DB.
     * @param image is the SpaceshipImage to save.
     * @return SpaceshipImage that was saved.
     */
    public SpaceshipImage saveImage(SpaceshipImage image) {
        return imageRepo.save(image);
    }

    /**
     * Find the Spaceship Image by its ID.
     * @param id the identification of the image.
     * @return SpaceshipImage.
     */
    public Optional<SpaceshipImage> getImage(String id) {
        return imageRepo.findById(id);
    }

    /**
     * Find Spaceship Image by Name.
     * @param name the name of the image.
     * @return SpaceshipImage.
     */
    public Optional<SpaceshipImage> findByName(String name) {
        return imageRepo.findByFileName(name);
    }

    /**
     * Get IDs and Names of all the images in the DB.
     * @return List<IdName>.
     */
    public List<IdName> findIdNames() {
        return imageRepo.findIdNameBy();
    }
}
