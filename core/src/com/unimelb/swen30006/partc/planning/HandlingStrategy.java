package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;

/**
 * The interface for handling the car's movement
 */

public interface HandlingStrategy {
    /**
     *
     * @param perceptionResponse: the car's surrounding objects.
     * @param state: the current state of the car
     * @return: an instance of Action which is the next step the car should take
     */
    public Action getAction(PerceptionResponse perceptionResponse, CarState state);
}
