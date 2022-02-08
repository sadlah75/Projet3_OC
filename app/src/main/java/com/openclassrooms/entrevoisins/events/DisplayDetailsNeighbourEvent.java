package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user want to display the Neighbour's details
 */
public class DisplayDetailsNeighbourEvent {
    /**
     * Neighbour to display
     */
    public Neighbour neighbour;

    /**
     * Constructor
     * @param neighbour
     */
    public DisplayDetailsNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
