package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;

/**
 * It copies some data from the Car to reducing the coupling with Car
 * also it contains some calculated data
 */
public class CarState {
    /**
     * the state of the car, describes what the car should do
     */
    public enum State{
        LEFT,
        RIGHT,
        STRAIGHT,
        ARRIVING,
        REACH_DEST
    }

    private State state;
    private float angle;
    private float shift;
    private float rotation;
    private Point2D.Double pos;
    private Vector2 velocity;

    /**
     *
     * @param state: an enum variable, the state of the should be
     * @param shift: The distance from the center of the road left + right -
     * @param angle: The angle difference between the road and the car moving angle.
     **/

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

    public Point2D.Double getPos() {
        return pos;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * @return the rotation in form of Vector2
     */

    public Vector2 getDirection(){
        double r = this.rotation * Math.PI/180f;
        double x = Math.cos(r);
        double y = Math.sin(r);
        return new Vector2((float)x, (float)y);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }


    public void setPos(Point2D.Double pos) {
        this.pos = pos;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public String toString(){
        return this.state.toString() + " " + this.angle + " " + this.shift + " " + this.velocity.len();
    }
}
