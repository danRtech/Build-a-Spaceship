package org.danRtech.spaceshipdata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Spaceship classification.
 */
@Table(name="spaceship")
@Entity
public class Spaceship {
    @Id
    private String code;

    @Column
    private String name;

    protected Spaceship(){
    }

    public Spaceship(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
