package org.danRtech.spaceshipdata.service;

import org.danRtech.spaceshipdata.model.entity.Spaceship;
import org.danRtech.spaceshipdata.model.entity.SpaceshipComponent;
import org.danRtech.spaceshipdata.model.enums.SpaceshipSize;
import org.danRtech.spaceshipdata.model.enums.SpaceshipType;
import org.danRtech.spaceshipdata.repository.SpaceshipComponentRepo;
import org.danRtech.spaceshipdata.repository.SpaceshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceshipComponentService {

    private SpaceshipRepo spaceshipRepo;
    private SpaceshipComponentRepo spaceshipComponentRepo;

    @Autowired
    public SpaceshipComponentService(SpaceshipRepo spaceshipRepo, SpaceshipComponentRepo spaceshipComponentRepo) {
        this.spaceshipRepo = spaceshipRepo;
        this.spaceshipComponentRepo = spaceshipComponentRepo;
    }

    public SpaceshipComponent createSpaceshipComponent(
            String spaceshipName, String title, String description,
            Integer price, String timeToBuild, String features, String keywords,
            SpaceshipType spaceshipType, SpaceshipSize spaceshipSize){

        Spaceship spaceship = spaceshipRepo.findByName(spaceshipName)
                .orElseThrow(() -> new RuntimeException("Spaceship not found by ID: " + spaceshipName));

        return spaceshipComponentRepo.save(new SpaceshipComponent(title, description, price,
                timeToBuild, features, keywords, spaceship, spaceshipType, spaceshipSize));
    }

    public long countSpaceshipComponents(){
        return spaceshipComponentRepo.count();
    }

}
