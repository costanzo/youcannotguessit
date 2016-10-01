package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.tong.Navigation;

/**
 * Created by Sean on 10/1/2016.
 */
public interface PriorityStrategy {
    public PerceptionResponse getHighesPriority(PerceptionResponse[] perceptionResponses, Navigation.CarState state);
}
