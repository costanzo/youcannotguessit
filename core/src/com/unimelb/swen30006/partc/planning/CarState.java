package com.unimelb.swen30006.partc.planning;

/**
 * Created by Sean on 10/6/2016.
 */
public class CarState {
    public enum State{
        LEFT,
        RIGHT,
        STRAIGHT,
        REACH_DEST
    }

    public  State state;
    public  float angle;
    public  float shift;

    /*
    state: The state of the should be
    shift: The distance from the center of the road left + right -
    angle: The angle difference between the road and the car moving angle.
     */

    public CarState(State state, float shift, float angle) {
        this.state = state;
        this.angle = angle;
        this.shift = shift;
    }

    public State getState() {
        return state;
    }

    public float getAngle() {
        return angle;
    }

    public float getShift() {
        return shift;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setShift(float shift) {
        this.shift = shift;
    }

    public String toString(){
        return this.state.toString() + " " + this.angle + " " + this.shift;
    }
}
