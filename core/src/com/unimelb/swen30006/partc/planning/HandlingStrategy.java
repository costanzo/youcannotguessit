package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;

/**
 * The interface for handling the car's movement
 */

public interface HandlingStrategy {
    /**
     * This method will create an Action to handle the car based on the perception and car state
     * @param perceptionResponse: perception response with the highest priority
     * @param state: the current state of the car
     * @return: an instance of Action which is the next step the car should take
     */
    public Action getAction(PerceptionResponse perceptionResponse, CarState state);
}
