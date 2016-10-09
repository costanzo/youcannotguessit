package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.tong.Action;
import com.unimelb.swen30006.partc.tong.Navigation;

/**
 * Created by Sean on 10/6/2016.
 */
public class SimpleHandlingStrategy implements HandlingStrategy {
    public static float TURNING_SPEED = 6f;
    public static float TURNING_MARGIN = 1f;
    public static float LANE_MARGIN = 5f;

    public static float TURNING_RATE = 0.3f;

    private Car car;

    public SimpleHandlingStrategy(Car car){
        this.car = car;
    }

    public Action getAction(PerceptionResponse perceptionResponse, CarState state){
        //float turningAngle = adjustPosture(state.angle, state.shift);
        float turningAngle = state.angle;
        if(state.state == CarState.State.STRAIGHT){
            if(perceptionResponse == null){
                return new Action(true, false, turningAngle);
            } else if(perceptionResponse.objectType == PerceptionResponse.Classification.TrafficLight){
                if(this.car.getVelocity().len() > (TURNING_SPEED + TURNING_MARGIN)){
                    return new Action(false, true, turningAngle);
                } else if(this.car.getVelocity().len() < (TURNING_SPEED - TURNING_MARGIN)){
                    return new Action(true, false, turningAngle);
                } else {
                    return new Action(false, false, turningAngle);
                }
            } else {
                return new Action(false, true, turningAngle);
            }
        } else if(state.state == CarState.State.LEFT){
            if(this.car.getVelocity().len() > (TURNING_SPEED + TURNING_MARGIN)){
                return new Action(false, true, turningAngle/10);
            } else if(this.car.getVelocity().len() < (TURNING_SPEED - TURNING_MARGIN)){
                return new Action(true, false, turningAngle/10);
            } else {
                return new Action(false, false, turningAngle/10);
            }
        } else {
            if(this.car.getVelocity().len() > (TURNING_SPEED + TURNING_MARGIN)){
                return new Action(false, true, -TURNING_RATE);
            } else if(this.car.getVelocity().len() < (TURNING_SPEED - TURNING_MARGIN)){
                return new Action(true, false, -TURNING_RATE);
            } else {
                return new Action(false, false, -TURNING_RATE);
            }
        }
    }

    private float adjustPosture(float angle, float shift){
        float posShift = shift - LANE_MARGIN;
        if(angle >= 0 && posShift >= 0){
            return 0-angle;
        } else if( angle < 0 && posShift >= 0){
            return angle/2;
        } else if(angle >= 0 && posShift < 0){
            return angle/2;
        } else{
            return 0-angle;
        }
    }
}
