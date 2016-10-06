package com.unimelb.swen30006.partc.planning;

/**
 * Created by Sean on 10/6/2016.
 */
public class CarState {
    public enum State{
        LEFT,
        RIGHT,
        STRAIGHT
    }

    public final State state;
    public final float angle;
    public final float shift;

    public CarState(State state, float shift, float angle) {
        this.state = state;
        this.angle = angle;
        this.shift = shift;
    }
}
