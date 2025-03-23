package org.danRtech.spaceshipdata.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.danRtech.spaceshipdata.model.entity.ComponentRating;
import org.danRtech.spaceshipdata.model.entity.SpaceshipComponent;
import org.danRtech.spaceshipdata.repository.ComponentRatingRepo;
import org.danRtech.spaceshipdata.repository.SpaceshipComponentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ComponentRatingServiceTest {
    private static final int PILOT_ID = 123;
    private static final int COMPONENT_ID = 1;
    private static final int COMPONENT_RATING_ID = 100;

    @Mock
    private SpaceshipComponentRepo spaceshipComponentRepositoryMock;
    @Mock
    private ComponentRatingRepo componentRatingRepositoryMock;

    @InjectMocks
    private ComponentRatingService componentRatingService;

    @Mock
    private SpaceshipComponent spaceshipComponentMock;

    @Mock
    private ComponentRating componentRatingMock;

    @Mock
    private ComponentRating componentRatingMock2;


    /**************************************************************************************
     *
     * Verify the service return value
     *
     **************************************************************************************/
    @Test
    public void lookupRatingById() {
        when(componentRatingRepositoryMock.findById(COMPONENT_RATING_ID)).thenReturn(Optional.of(componentRatingMock));

        // invoke and verify lookupRatingById
        assertThat(componentRatingService.lookupRatingById(COMPONENT_RATING_ID).get(), is(componentRatingMock));
    }

    @Test
    public void lookupAll() {
        when(componentRatingRepositoryMock.findAll()).thenReturn(Arrays.asList(componentRatingMock));

        // invoke and verify lookupAll
        assertThat(componentRatingService.lookupAll().get(0), is(componentRatingMock));
    }

    @Test
    public void getAverageScore() {
        when(spaceshipComponentRepositoryMock.findById(COMPONENT_ID)).thenReturn(Optional.of(spaceshipComponentMock));
        when(spaceshipComponentMock.getId()).thenReturn(COMPONENT_ID);
        when(componentRatingRepositoryMock.findByComponentId(COMPONENT_ID)).thenReturn(Arrays.asList(componentRatingMock));
        when(componentRatingMock.getScore()).thenReturn(10);

        // invoke and verify getAverageScore
        assertThat(componentRatingService.getAverageScore(COMPONENT_ID), is(10.0));
    }

    @Test
    public void lookupRatings() {
        // create mocks of Pageable and Page (only needed in this test)
        List list = mock(List.class);
        when(spaceshipComponentRepositoryMock.findById(COMPONENT_ID)).thenReturn(Optional.of(spaceshipComponentMock));
        when(spaceshipComponentMock.getId()).thenReturn(COMPONENT_ID);

        when(componentRatingRepositoryMock.findByComponentId(COMPONENT_ID)).thenReturn(list);

        // invoke and verify lookupRatings
        assertThat(componentRatingService.lookupRatingsByComponent(COMPONENT_ID), is(list));
    }

    /**************************************************************************************
     *
     * Verify the invocation of dependencies.
     *
     **************************************************************************************/

    @Test
    public void delete() {
        when(componentRatingRepositoryMock.findByComponentIdAndPilotId(COMPONENT_ID, PILOT_ID))
                .thenReturn(Optional.of(componentRatingMock));

        // invoke delete
        componentRatingService.delete(1, PILOT_ID);

        // verify componentRatingRepository.delete invoked
        verify(componentRatingRepositoryMock).delete(any(ComponentRating.class));
    }

    @Test
    public void rateMany() {
        when(spaceshipComponentRepositoryMock.findById(COMPONENT_ID)).thenReturn(Optional.of(spaceshipComponentMock));
        when(componentRatingRepositoryMock.findByComponentIdAndPilotId(COMPONENT_ID, PILOT_ID))
                .thenReturn(Optional.empty());
        when(componentRatingRepositoryMock.findByComponentIdAndPilotId(COMPONENT_ID, PILOT_ID + 1))
                .thenReturn(Optional.empty());

        // invoke rateMany
        componentRatingService.rateMany(COMPONENT_ID, 10, List.of(PILOT_ID, PILOT_ID + 1));

        // verify componentRatingRepository.save invoked twice
        verify(componentRatingRepositoryMock, times(2)).save(any(ComponentRating.class));
    }

    @Test
    public void update() {
        when(componentRatingRepositoryMock.findByComponentIdAndPilotId(COMPONENT_ID, PILOT_ID))
                .thenReturn(Optional.of(componentRatingMock));

        // invoke update
        componentRatingService.update(COMPONENT_ID, PILOT_ID, 5, "great");

        // verify componentRatingRepository.save invoked once
        verify(componentRatingRepositoryMock).save(any(ComponentRating.class));

        // verify and componentRating setter methods invoked
        verify(componentRatingMock).setComment("great");
        verify(componentRatingMock).setScore(5);
    }

    @Test
    public void updateSome() {
        when(componentRatingRepositoryMock.findByComponentIdAndPilotId(COMPONENT_ID, PILOT_ID))
                .thenReturn(Optional.of(componentRatingMock));

        // invoke updateSome
        componentRatingService.updateSome(COMPONENT_ID, PILOT_ID, Optional.of(1), Optional.of("awful"));

        // verify tourRatingRepository.save invoked once
        verify(componentRatingRepositoryMock).save(any(ComponentRating.class));

        // verify and componentRating setter methods invoked
        verify(componentRatingMock).setComment("awful");
        verify(componentRatingMock).setScore(1);
    }

    /**************************************************************************************
     *
     * Verify the invocation of dependencies
     * Capture parameter values.
     * Verify the parameters.
     *
     **************************************************************************************/

    @Test
    public void createNew() {
        when(spaceshipComponentRepositoryMock.findById(COMPONENT_ID)).thenReturn(Optional.of(spaceshipComponentMock));
        // prepare to capture a ComponentRating Object
        ArgumentCaptor<ComponentRating> tourRatingCaptor = ArgumentCaptor.forClass(ComponentRating.class);

        // invoke createNew
        componentRatingService.createNew(COMPONENT_ID, PILOT_ID, 2, "ok");

        // verify componentRatingRepository.save invoked once and capture the ComponentRating Object
        verify(componentRatingRepositoryMock).save(tourRatingCaptor.capture());

        // verify the attributes of the Component Rating Object
        assertThat(tourRatingCaptor.getValue().getComponent(), is(spaceshipComponentMock));
        assertThat(tourRatingCaptor.getValue().getPilotId(), is(PILOT_ID));
        assertThat(tourRatingCaptor.getValue().getScore(), is(2));
        assertThat(tourRatingCaptor.getValue().getComment(), is("ok"));
    }

    /**
     * Verify unhappy path
     */
    @Test
    public void testNotFound() {
        when(spaceshipComponentRepositoryMock.findById(COMPONENT_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                componentRatingService.lookupRatingsByComponent(COMPONENT_ID)
        );
    }


}
