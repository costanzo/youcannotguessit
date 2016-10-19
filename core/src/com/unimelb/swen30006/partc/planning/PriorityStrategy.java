package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;

/**
 *  the interface is to prioritise the the perception responses based on the car state
 */
public interface PriorityStrategy {

    /**
     * This method is used to select the highest priority perception response
     * from the all the perception responses based on car state
     * @param perceptionResponses
     * @param state
     * @return the perception response with the highest priority
     */
    public PerceptionResponse getHighestPriority(PerceptionResponse[] perceptionResponses, CarState state);
}
