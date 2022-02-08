package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import android.util.Log;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createNeighbourWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbourToAdd = new Neighbour();
        neighbours.add(neighbourToAdd);
        assertEquals(13,neighbours.size());
    }

    @Test
    public void modifyNeighbourWithSuccess() {
        Neighbour neighbourToModify = service.getNeighbours().get(0);
        neighbourToModify.setFavorite(true);
        service.modifyNeighbour(neighbourToModify);
        assertTrue(neighbourToModify.isFavorite());
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbourToAdd = new Neighbour();
        neighbourToAdd.setFavorite(true);
        neighbours.add(neighbourToAdd);

        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        assertEquals(1,favoriteNeighbours.size());

    }

    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.addFavoriteNeighbour(neighbourToDelete);
        favoriteNeighbours = service.getFavoriteNeighbours();
        service.deleteFavoriteNeighbour(neighbourToDelete);
    }

}
