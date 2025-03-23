package org.danRtech.spaceshipdata;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.commons.lang3.StringUtils;
import org.danRtech.spaceshipdata.service.SpaceshipComponentService;
import org.danRtech.spaceshipdata.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpaceshipDataApplication implements CommandLineRunner {


	/**
	 * Displays the API description at the header level in tools like Swagger.
	 */
	@Bean
	public OpenAPI swaggerHeader(){
		return new OpenAPI()
				.info((new Info())
				.description("Services for the Build a Spaceship database.")
				.title(StringUtils.substringBefore(getClass().getSimpleName(), "$"))
				.version("3.0.0"));
	}

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
		System.out.println("Persisted ships = " + spaceshipService.countSpaceships());
		System.out.println("Persisted components = " + spaceshipComponentService.countSpaceshipComponents());
	}
}
