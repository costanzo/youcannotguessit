package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;


/**
 * This class will create action that will handle the movement of the car
 */
public class SimpleHandlingStrategy implements HandlingStrategy {
    //the speed constraint when crossing intersection
    //this value is different from the diagram
    public static final float TURNING_SPEED = 12f;

    //the shift increase when the car is arriving the destination
    public static final float ARRIVING_SHIFT = 6f;

    //basic turning rate for adjusting the state when go straight
    public static final float TURNING_RATE = 0.3f;

    //turning rate for turning left
    public static final float LEFT_TURN = 2f;

    //turning rate for turning right
    //this value is different from the diagram
    public static final float RIGHT_TURN = -0.6f;

    //the distance that the car should maintain from the center of the road
    //this value is different from the diagram
    public static final float LANE_MARGIN = 4f;

    //the threshold of angle for different turning strategy
    public static final float ADJUST_TURNING_THRES = 0.5f;

    //adjust coefficient when going straight
    //this value is different from the diagram
    public static final float ADJUST_COEFF = 20f;

    //turning coefficient when turning left or right
    public static final float TURNING_COEFF = 10f;

    //the increase of distance from the center of the road to the car
    private float arrivingShift;

    public SimpleHandlingStrategy(){
    }

    /**
     * get the action that the car should take based on the highest perception response and car state
     * @param perceptionResponse the highest priority perception response generated
     * @param state the state the car is currently in
     * @return  the actio that the car should take
     */
    public Action getAction(PerceptionResponse perceptionResponse, CarState state){
        //the angle between the direction of car and the direction of the road
        float turningAngle = state.getAngle();
        Vector2 velo = state.getVelocity();
        CarState.State st = state.getState();
        float speed = velo.len();

        //the distance between the car and the center of the road
        float sh = state.getShift();

        Action action;
        switch (st){
            case REACH_DEST:
                //stop the car
                action = new Action(false, true, 0);
                break;
            case ARRIVING:
                //try to let the car move close to the edge of the road
                this.arrivingShift = ARRIVING_SHIFT;
                action = goStraight(turningAngle, perceptionResponse, sh, speed);
                break;
            case STRAIGHT:
                //try to let the car move close the center of the road
                this.arrivingShift = 0;
                action = goStraight(turningAngle, perceptionResponse, sh, speed);
                break;
            case LEFT:
                action = turning(speed, LEFT_TURN);
                break;
            default:
                action = turning(speed, RIGHT_TURN);
                break;
        }
        return action;
    }

    //get the action when the car is turning right or left
    private Action turning(float speed, float turnSpeed){
        if(speed > TURNING_SPEED){
            //too fast, slow down
            return new Action(false, true, turnSpeed);
        } else if(speed < TURNING_SPEED){
            //too slow, speed up
            return new Action(true, false, turnSpeed);
        } else {
            //right speed
            return new Action(false, false, turnSpeed);
        }
    }

    //get the action when the car is going straight or trying to pull over
    private Action goStraight(float turningAngle, PerceptionResponse perceptionResponse, float sh, float speed){
        //the base turn rate for adjusting the position
        float turn = 0;
        if(turningAngle == 0) {
            turn = 0;
        }
        else if (turningAngle < 0) {
            turn = TURNING_RATE;
        }
        else {
            turn = -TURNING_RATE;
        }

        if(perceptionResponse == null){
            //there is nothing ahead can affect the running car
            if(sh < 0 || Math.abs(turningAngle) > ADJUST_TURNING_THRES ) {
                //the car is in the intersection or the angle of the car is very big that may cause instability
                return new Action(true, false, turn * Math.abs(turningAngle)/TURNING_COEFF);
            } else{
                if(speed < 15f){
                    //car runs slow, can have big adjust
                    return new Action(true, false, turn * 3);
                }
                //the car is almost stable and can do minor adjusting
                return new Action(true, false, TURNING_RATE * (LANE_MARGIN-sh + this.arrivingShift) * ADJUST_COEFF * 20/(speed+5));//(Car.MAX_VELOCITY - speed)/10);
            }
        } else if(perceptionResponse.objectType == PerceptionResponse.Classification.TrafficLight){
            //the car is close to the intersection
            if(perceptionResponse.information.get("state") != Color.GREEN){
                //the car stops when the light is not green
                return new Action(false, true, turn);
            } else {
                //the car is going to cross the intersection
                if(speed > TURNING_SPEED){
                    //too fast, slow down
                    return new Action(false, true, turn);
                } else{
                    //too slow, speed up
                    return new Action(true, false, turn);
                }
            }
        } else {
            //there is a obstacle ahead, the car should stop and wait
            return new Action(false, true, turn);
        }
    }

}
