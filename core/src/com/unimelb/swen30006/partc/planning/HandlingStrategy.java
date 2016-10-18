package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;

/**
 * Created by Sean on 10/6/2016.
 */
public interface HandlingStrategy {
    public Action getAction(PerceptionResponse perceptionResponse, CarState state);
}
