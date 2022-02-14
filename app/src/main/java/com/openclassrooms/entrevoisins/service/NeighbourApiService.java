package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Modify a neighbour
     * @param neighbour
     */
    void modifyNeighbour(Neighbour neighbour);

    /**
     * Get all my Favorite Neighbours
     * @return {@link List}
     */
    List<Neighbour> getFavoriteNeighbours();

    /**
     * Add a Favorite Neighbour
     * @param neighbourToAdd
     */
    void addFavoriteNeighbour(Neighbour neighbourToAdd);

    /**
     * Delete a Favorite Neighbour
     * @param neighbourToDelete
     */
    void deleteFavoriteNeighbour(Neighbour neighbourToDelete);

    /**
     *
     * @param id
     * @return
     */
    Neighbour getNeighbourById(int id);
}
