package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    //--------- sadek --------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void modifyNeighbour(Neighbour neighbour) {
        neighbours.set(neighbours.indexOf(neighbour),neighbour);
    }

    /**
     * {@inheritDoc}
     * @return {@link List}
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> lFavoriteNeighbourList = new ArrayList<>();
        for (Neighbour neighbourFavorite: neighbours) {
            if(neighbourFavorite.isFavorite()) {
                lFavoriteNeighbourList.add(neighbourFavorite);
            }
        }
        return lFavoriteNeighbourList;
    }

    /**
     * Add a favorite neighbour
     * @param neighbourToAdd
     */
    @Override
    public void addFavoriteNeighbour(Neighbour neighbourToAdd) {
        neighbours.get(neighbours.indexOf(neighbourToAdd)).setFavorite(true);
    }

    /**
     * Delete a favorite neighbour
     * @param neighbourToDelete
     */
    @Override
    public void deleteFavoriteNeighbour(Neighbour neighbourToDelete) {
        neighbours.get(neighbours.indexOf(neighbourToDelete)).setFavorite(false);
        getFavoriteNeighbours().remove(neighbourToDelete);
    }

    @Override
    public Neighbour getNeighbourById(int id) {
        for (Neighbour n: getNeighbours()) {
            if(n.getId() == id) {
             return n;
            }
        }
        return null;
    }
}
