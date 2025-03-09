package org.danRtech.spaceshipdata.model;

import jakarta.persistence.*;

/**
 * Contains all attributes of a Spaceship Component
 */
@Entity
public class SpaceshipComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column(length = 2000)
    private String description;

    @Column
    private Integer price;

    @Column
    private String timeToBuild;

    @Column(length = 2000)
    private String features;

    @Column
    private String keywords;

    @ManyToOne
    @JoinColumn(name = "spaceship_code")
    private Spaceship spaceship;

    @Column
    @Enumerated(EnumType.STRING)
    private SpaceshipType spaceshipType;

    @Column
    private SpaceshipSize spaceshipSize;

    public SpaceshipComponent(String title, String description, Integer price,
                              String timeToBuild, String features, String keywords, Spaceship spaceship,
                              SpaceshipType spaceshipType, SpaceshipSize spaceshipSize) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.timeToBuild = timeToBuild;
        this.features = features;
        this.keywords = keywords;
        this.spaceship = spaceship;
        this.spaceshipType = spaceshipType;
        this.spaceshipSize = spaceshipSize;
    }

    public SpaceshipComponent() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTimeToBuild() {
        return timeToBuild;
    }

    public void setTimeToBuild(String timeToBuild) {
        this.timeToBuild = timeToBuild;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    public SpaceshipType getSpaceshipType() {
        return spaceshipType;
    }

    public void setSpaceshipType(SpaceshipType spaceshipType) {
        this.spaceshipType = spaceshipType;
    }

    public SpaceshipSize getSpaceshipSize() {
        return spaceshipSize;
    }

    public void setSpaceshipSize(SpaceshipSize spaceshipSize) {
        this.spaceshipSize = spaceshipSize;
    }
}
