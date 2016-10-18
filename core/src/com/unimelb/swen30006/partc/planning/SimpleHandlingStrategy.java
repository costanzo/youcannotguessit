package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.tong.Action;


/**
 * Created by Sean on 10/6/2016.
 */
public class SimpleHandlingStrategy implements HandlingStrategy {
    public static final float TURNING_SPEED = 12f;
    public static final float ARRIVING_SHIFT = 6f;

    public static final float TURNING_RATE = 0.3f;
    public static final float LEFT_TURN = 2f;
    public static final float RIGHT_TURN = -0.6f;

    public static final float LANE_MARGIN = 4f;
    public static final float ADJUST_TURNING_THRES = 0.5f;

    public static final float ADJUST_COEFF = 20f;
    public static final float TURNING_COEFF = 10f;

    private float arrivingShift;

    public SimpleHandlingStrategy(){
    }

    public Action getAction(PerceptionResponse perceptionResponse, CarState state){
        float turningAngle = state.getAngle();
        Vector2 velo = state.getVelocity();
        CarState.State st = state.getState();
        float speed = velo.len();
        float sh = state.getShift();

        Action action;
        switch (st){
            case REACH_DEST:
                action = new Action(false, true, 0);
                break;
            case ARRIVING:
                this.arrivingShift = ARRIVING_SHIFT;
                action = goStraight(turningAngle, perceptionResponse, sh, speed);
                break;
            case STRAIGHT:
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

    private Action turning(float speed, float turnSpeed){
        if(speed > TURNING_SPEED){
            return new Action(false, true, turnSpeed);
        } else if(speed < TURNING_SPEED){
            return new Action(true, false, turnSpeed);
        } else {
            return new Action(false, false, turnSpeed);
        }
    }

    private Action goStraight(float turningAngle, PerceptionResponse perceptionResponse, float sh, float speed){
        float turn = 0;
        if(turningAngle == 0)
            turn = 0;
        else if (turningAngle < 0)
            turn = TURNING_RATE;
        else
            turn = -TURNING_RATE;

        if(perceptionResponse == null){
            if(sh < 0 || Math.abs(turningAngle) > ADJUST_TURNING_THRES ) {
                //the car is in the intersection or the angle of the car is very big that may cause instability
                return new Action(true, false, turn * Math.abs(turningAngle)/TURNING_COEFF);
            } else{
                if(speed < 15f){
                    return new Action(true, false, turn * 3);
                }
                //the car is almost stable and can adjust now
                return new Action(true, false, TURNING_RATE * (LANE_MARGIN-sh + this.arrivingShift) * ADJUST_COEFF * 20/(speed+5));//(Car.MAX_VELOCITY - speed)/10);
            }
        } else if(perceptionResponse.objectType == PerceptionResponse.Classification.TrafficLight){
            if(perceptionResponse.information.get("state") != Color.GREEN){
                return new Action(false, true, turn);
            } else {
                if(speed > TURNING_SPEED){
                    return new Action(false, true, turn);
                } else{
                    return new Action(true, false, turn);
                }
            }
        } else {
            return new Action(false, true, turn);

        }
    }

}
