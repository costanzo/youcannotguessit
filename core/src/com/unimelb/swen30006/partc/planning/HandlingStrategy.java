package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;

/**
 * The interface for handling the car's movement
 */

public interface HandlingStrategy {
    public Action getAction(PerceptionResponse perceptionResponse, CarState state);
}
