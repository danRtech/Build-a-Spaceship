package org.danRtech.spaceshipdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.danRtech.spaceshipdata.model.enums.SpaceshipSize;
import org.danRtech.spaceshipdata.model.enums.SpaceshipType;
import org.danRtech.spaceshipdata.service.SpaceshipComponentService;
import org.danRtech.spaceshipdata.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class SpaceshipDataApplication implements CommandLineRunner {

	private final String SPACESHIP_IMPORT_FILE = "SpaceshipComponents.json";

	private SpaceshipService spaceshipService;
	private SpaceshipComponentService spaceshipComponentService;

	@Autowired
	public SpaceshipDataApplication(SpaceshipService spaceshipService,
									SpaceshipComponentService spaceshipComponentService) {
		this.spaceshipService = spaceshipService;
		this.spaceshipComponentService = spaceshipComponentService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpaceshipDataApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		createSpaceship_AllComponents();
		System.out.println("Persisted ships = " + spaceshipService.countSpaceships());
		createSpaceshipComppnentsFromFile(SPACESHIP_IMPORT_FILE);
		System.out.println("Persisted components = " + spaceshipComponentService.countSpaceshipComponents());
	}

	/**
	 * Initialize all the known spaceships
	 */
	private void createSpaceship_AllComponents() {
		spaceshipService.createSpaceship("FX", "Falcon-X");
		spaceshipService.createSpaceship("SD", "Star Defender");
		spaceshipService.createSpaceship("RP", "Rogue Phoenix");
		spaceshipService.createSpaceship("GE", "Galactic Explorer");
		spaceshipService.createSpaceship("NC", "Nebula Cruiser");
	}

	/**
	 * Create Spaceship Component entities from an external file
	 */
	private void createSpaceshipComppnentsFromFile(String fileToImport) throws IOException {
		SpaceshipComponentFromFile.read(fileToImport).forEach(sc ->
				spaceshipComponentService.createSpaceshipComponent(
						sc.spaceshipName(),
						sc.title(),
						sc.description(),
						sc.price(),
						sc.timeToBuild(),
						sc.features(),
						sc.keywords(),
						sc.spaceshipType(),
						sc.spaceshipSize()
				)
		);
	}

	/**
	 * Helper to import SpaceshipComponents.json
	 */
	record SpaceshipComponentFromFile(String spaceshipName, String title, String description,
						Integer price, String timeToBuild, String features,
						String keywords, SpaceshipType spaceshipType, SpaceshipSize spaceshipSize) {
		static List<SpaceshipComponentFromFile> read(String fileToImport) throws IOException {
			return new ObjectMapper().readValue(new File(fileToImport),
					new TypeReference<List<SpaceshipComponentFromFile>>() {});
		}
	}
}
