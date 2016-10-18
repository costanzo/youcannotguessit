package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;

/**
 * Created by Sean on 10/1/2016.
 */
public interface PriorityStrategy {
    public PerceptionResponse getHighestPriority(PerceptionResponse[] perceptionResponses, CarState state);
}
