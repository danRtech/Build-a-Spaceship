package org.danRtech.spaceshipimages.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.danRtech.spaceshipimages.model.projection.IdName;
import org.danRtech.spaceshipimages.model.entity.SpaceshipImage;
import org.danRtech.spaceshipimages.service.SpaceshipImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Rest controller to handle spaceship images.
 */
@RestController
@Slf4j
@Tag(name = "Hosting of Spaceship Images", description = "Upload and fetch Images for the Build-a-Spaceship web application.")
@RequestMapping("/api/images")
public class SpaceshipImageController {

    private SpaceshipImageService service;

    @Autowired
    public SpaceshipImageController(SpaceshipImageService service) {
        this.service = service;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a file to add to the web app's collection of spaceship images")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        log.info("POST /api/images {}", file.getOriginalFilename());

        SpaceshipImage image = new SpaceshipImage();
        image.setFileName(file.getOriginalFilename());
        image.setData(file.getBytes());
        SpaceshipImage savedImage = service.saveImage(image);
        return savedImage.getId();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch the file by mongo identifier")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {

        log.info("GET /api/images/{}", id);

        Optional<SpaceshipImage> optionalImage = service.getImage(id);
        if (optionalImage.isPresent()) {
            SpaceshipImage image = optionalImage.get();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(image.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "List all filenames with their mongodb identifier")
    public List<IdName> findAll() {
        log.info("GET /api/images");
        return service.findIdNames();
    }
}

