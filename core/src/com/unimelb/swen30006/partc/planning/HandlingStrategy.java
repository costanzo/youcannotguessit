package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.tong.Action;
import com.unimelb.swen30006.partc.tong.Navigation;

/**
 * Created by Sean on 10/6/2016.
 */
public interface HandlingStrategy {
    public Action getAction(PerceptionResponse perceptionResponse, CarState state);
}
