package org.danRtech.spaceshipdata.web.dto;

import lombok.Data;

@Data
public class ScoreDto {

    private Double average;

    public ScoreDto(Double score) {
        this.average = score;
    }
}
