package org.danRtech.spaceshipdata.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.danRtech.spaceshipdata.model.entity.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * Making sure the url keyword is "spaceships". Change it to anything and see on Swagger.
 */
@RepositoryRestResource(path = "spaceships", collectionResourceRel = "spaceships")
@Tag(name = "Spaceship", description = "The Spaceship API")
public interface SpaceshipRepo extends JpaRepository<Spaceship, String> {

    // Note that the descriptions for the interface above and for the methods below are not going to work (won't be displayed in Swagger).
    // This is because Spring Data Rest is automatically generates endpoints and ignores the custom descriptions.
    // Hence, the @Tag and @Operation annotations should be removed in this case. Leaving them as an example for now.

    @Operation(description = "Finds Spaceship by name")
    Optional<Spaceship> findByName(String name);
}
