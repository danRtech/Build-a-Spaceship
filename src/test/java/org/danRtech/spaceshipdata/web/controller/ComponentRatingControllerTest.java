package org.danRtech.spaceshipdata.web.controller;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import java.util.NoSuchElementException;

import org.danRtech.spaceshipdata.model.entity.ComponentRating;
import org.danRtech.spaceshipdata.model.entity.SpaceshipComponent;
import org.danRtech.spaceshipdata.service.ComponentRatingService;
import org.danRtech.spaceshipdata.web.dto.RatingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ComponentRatingControllerTest {

    // These components and rating ids do not already exist in the db
    private static final int COMPONENT_ID = 999;
    private static final int PILOT_ID = 1000;
    private static final int SCORE = 3;
    private static final String COMMENT = "comment";
    private static final String COMPONENT_RATINGS_URL = "/components/" + COMPONENT_ID + "/ratings";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ComponentRatingService serviceMock;

    @Autowired
    private ComponentRating componentRatingMock;

    @Autowired
    private SpaceshipComponent componentMock;

    private RatingDto ratingDto = new RatingDto(SCORE, COMMENT, PILOT_ID);

    @Test
    void testCreateTourRating() {

        restTemplate.postForEntity(COMPONENT_RATINGS_URL, ratingDto, RatingDto.class);

        verify(this.serviceMock).createNew(COMPONENT_ID, PILOT_ID, SCORE, COMMENT);
    }

    @Test
    void testDelete() {

        restTemplate.delete(COMPONENT_RATINGS_URL + "/" + PILOT_ID);

        verify(this.serviceMock).delete(COMPONENT_ID, PILOT_ID);
    }

    @Test
    void testGetAllRatingsForComponent() {
        when(serviceMock.lookupRatingsByComponent(anyInt())).thenReturn(List.of(componentRatingMock));
        ResponseEntity<String> res = restTemplate.getForEntity(COMPONENT_RATINGS_URL, String.class);

        assertThat(res.getStatusCode(), is(HttpStatus.OK));
        verify(serviceMock).lookupRatingsByComponent(anyInt());
    }

    @Test
    void testGetAverage() {
        when(serviceMock.lookupRatingsByComponent(anyInt())).thenReturn(List.of(componentRatingMock));
        ResponseEntity<String> res = restTemplate.getForEntity(COMPONENT_RATINGS_URL + "/average", String.class);

        assertThat(res.getStatusCode(), is(HttpStatus.OK));
        verify(serviceMock).getAverageScore(COMPONENT_ID);
    }

    /*
     * PATCH testing only works when adding http client dependency to pom.xml
     */
    @Test
    void testUpdateWithPatch() {
        when(serviceMock.updateSome(anyInt(), anyInt(), any(), any())).thenReturn(componentRatingMock);

        restTemplate.patchForObject(COMPONENT_RATINGS_URL, ratingDto, String.class);
        verify(this.serviceMock).updateSome(anyInt(), anyInt(), any(), any());
    }

    @Test
    void testUpdateWithPut() {
        restTemplate.put(COMPONENT_RATINGS_URL, ratingDto);

        verify(this.serviceMock).update(COMPONENT_ID, PILOT_ID, SCORE, COMMENT);
    }

    @Test
    void testCreateManyTourRatings() {
        Integer customers[] = {123};
        restTemplate.postForObject(COMPONENT_RATINGS_URL + "/batch?score=" + SCORE, customers,
                String.class);

        verify(serviceMock).rateMany(anyInt(), anyInt(), anyList());
    }

    /** Test unhappy Paths too to validate GlobalExceptionHandler */

    @Test
    public void test404() {
        when(serviceMock.lookupRatingsByComponent(anyInt())).thenThrow(new NoSuchElementException());
        ResponseEntity<String> res = restTemplate.getForEntity(COMPONENT_RATINGS_URL, String.class);

        assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void test400() {
        when(serviceMock.lookupRatingsByComponent(anyInt())).thenThrow(new ConstraintViolationException(null));
        ResponseEntity<String> res = restTemplate.getForEntity(COMPONENT_RATINGS_URL, String.class);

        assertThat(res.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    @TestConfiguration
    static class TestMockConfig {

        @Bean
        public ComponentRatingService componentRatingService() {
            return mock(ComponentRatingService.class);
        }

        @Bean
        public ComponentRating componentRating() {
            return mock(ComponentRating.class);
        }

        @Bean
        public SpaceshipComponent spaceshipComponent() {
            return mock(SpaceshipComponent.class);
        }
    }

}
