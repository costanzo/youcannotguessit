package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.tong.Action;
import com.unimelb.swen30006.partc.tong.Navigation;

/**
 * Created by Sean on 10/6/2016.
 */
public class SimpleHandlingStrategy implements HandlingStrategy {
    public static float TURNING_SPEED = 10f;
    public static float TURNING_MARGIN = 1f;
    public static float TURNING_RATE   = 3f;

    private Car car;

    public Action getAction(PerceptionResponse perceptionResponse, CarState state){
        if(state.state == CarState.State.STRAIGHT){
            if(perceptionResponse == null){
                return new Action(true, false, 0);
            } else if(perceptionResponse.objectType == PerceptionResponse.Classification.TrafficLight){
                if(this.car.getVelocity().len() > (TURNING_SPEED + TURNING_MARGIN)){
                    return new Action(false, true, 0);
                } else if(this.car.getVelocity().len() < (TURNING_SPEED - TURNING_MARGIN)){
                    return new Action(true, false, 0);
                } else {
                    return new Action(false, false, 0);
                }
            } else {

            }

        } else if(state.state == CarState.State.LEFT){

        } else {

        }
    }

    public Action adjustPosture(float angle){

    }
}
