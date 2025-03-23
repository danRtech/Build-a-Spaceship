package org.danRtech.spaceshipdata.service;

import org.danRtech.spaceshipdata.model.entity.Spaceship;
import org.danRtech.spaceshipdata.repository.SpaceshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceshipService {

    private SpaceshipRepo spaceshipRepo;

    @Autowired
    public SpaceshipService(SpaceshipRepo spaceshipRepo) {
        this.spaceshipRepo = spaceshipRepo;
    }

    public Spaceship createSpaceship(String code, String name){
        return spaceshipRepo.findById(code)
                .orElse(spaceshipRepo.save(new Spaceship(code, name)));
    }

    public List<Spaceship> findAllSpaceships(){
        return spaceshipRepo.findAll();
    }

    public long countSpaceships(){
        return spaceshipRepo.count();
    }
}
