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
        Neighbour neighbourToAdd = new Neighbour(13,
                "Hatem",
                "https://i.pravatar.cc/150?u=a042581f3e39026702d",
                "rue de Jules Verne",
                "+33 12 34 56 78 90",
                "Bonjour à tous ...");
        neighbours.add(neighbourToAdd);
        assertEquals("Hatem",service.getNeighbourById(13).getName());
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
        //On récupère la liste des voisins
        List<Neighbour> neighbours = service.getNeighbours();
        /*
            1- On crée un voisin avec un id 13
            2- On lui affecte le statut de favoris
            3- On l'ajoute à la liste des voisins
         */
        Neighbour neighbourToAdd = new Neighbour(13,
                "Hatem",
                "https://i.pravatar.cc/150?u=a042581f3e39026702d",
                "rue de Jules Verne",
                "+33 12 34 56 78 90",
                "Bonjour à tous ...");
        neighbours.add(neighbourToAdd);
        service.addFavoriteNeighbour(neighbourToAdd);

        /*
            1- on récupère de la liste des voisins, l'élèment d'Id=13 passé en
            paramètre.
            2- On vérifie qu'il s'agit bien de l'élèment ajouté précèdemment
         */
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour neighbourToCheck = service.getNeighbourById(13);
        assertTrue(favoriteNeighbours.contains(neighbourToCheck));
        /* ou bien
            assertEquals("Hatem",neighbourToCheck.getName());
         */
    }


    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        /* 1- On récupère le 1er élèment de la liste des Neighbours d'index 0
           2- puis on l'ajoute à la liste des favoris
           3- On vérifie que la liste des favoris contient bien l'élément ajouté et pas un autre
        */
        Neighbour neighbourToFavoris = service.getNeighbours().get(0);
        service.addFavoriteNeighbour(neighbourToFavoris);
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        assertTrue(favoriteNeighbours.contains(neighbourToFavoris));

        /*
            1- On supprime l'élément récemment ajouté dans les favoris
            2- Puis on vérifie que la liste des favoris ne contient plus cet élément
         */
        service.deleteFavoriteNeighbour(neighbourToFavoris);
        List<Neighbour> favoriteNeighboursToCheck = service.getFavoriteNeighbours();
        assertFalse(favoriteNeighboursToCheck.contains(neighbourToFavoris));

    }

}