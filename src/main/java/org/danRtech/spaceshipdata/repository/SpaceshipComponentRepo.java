package org.danRtech.spaceshipdata.repository;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.danRtech.spaceshipdata.model.entity.SpaceshipComponent;
import org.danRtech.spaceshipdata.model.enums.SpaceshipSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Tag(name = "Spaceship Component", description = "The Spaceship Component API")
public interface SpaceshipComponentRepo extends JpaRepository<SpaceshipComponent, Integer> {

    // Note that the descriptions for the interface above and for the methods below are not going to work (won't be displayed in Swagger).
    // This is because Spring Data Rest is automatically generates endpoints and ignores the custom descriptions.
    // Hence, the @Tag and @Operation annotations should be removed in this case.  Leaving them as an example for now.

    @Operation(description = "Returns the list of Spaceships Components by Spaceship Size.")
    List<SpaceshipComponent> findBySpaceshipSize(SpaceshipSize spaceshipSize);

    @Operation(description = "Returns the list of Spaceship Components by Spaceship Code.")
    List<SpaceshipComponent> findBySpaceshipCode(String spaceshipCode);
}
