package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.tong.Action;
import com.unimelb.swen30006.partc.tong.Navigation;

/**
 * Created by Sean on 10/6/2016.
 */
public class SimpleHandlingStrategy implements HandlingStrategy {
    public static final float TURNING_SPEED = 6f;

    public static final float TURNING_RATE = 0.3f;
    public static final float LEFT_TURN = 6f;

    public static final float LANE_MARGIN = 5f;
    public static final float ADJUST_TURNING_THRES = 0.5f;

    public static final float ADJUST_COEFF = 4f;

    private Car car;

    public SimpleHandlingStrategy(Car car){
        this.car = car;
    }

    public Action getAction(PerceptionResponse perceptionResponse, CarState state){
        //float turningAngle = adjustPosture(state.angle, state.shift);
        float turningAngle = state.getAngle();
        CarState.State st = state.getState();
        if(st == CarState.State.REACH_DEST){
            return new Action(false, true, 0);
        }else if(st == CarState.State.STRAIGHT){
            float turn = 0;
            if(turningAngle == 0)
                turn = 0;
            else if (turningAngle < 0)
                turn = TURNING_RATE;
            else
                turn = -TURNING_RATE;

            if(perceptionResponse == null){
                float sh = state.getShift();
                if(sh < 0 || Math.abs(turningAngle) > ADJUST_TURNING_THRES  ) {
                    return new Action(true, false, turn);
                } else{
                    if(sh > LANE_MARGIN){
                        return new Action(true, false, -TURNING_RATE * (sh-LANE_MARGIN) * ADJUST_COEFF);
                    } else if (sh < LANE_MARGIN){
                        return new Action(true, false, TURNING_RATE * (LANE_MARGIN-sh) * ADJUST_COEFF);
                    } else {
                        return new Action(true, false, 0);
                    }
                }
            } else if(perceptionResponse.objectType == PerceptionResponse.Classification.TrafficLight){
                return new Action(false, true, turn);
            } else {
                return new Action(false, true, turn);

            }
        } else if(st == CarState.State.LEFT){
            if(this.car.getVelocity().len() > TURNING_SPEED){
                return new Action(false, true, LEFT_TURN);
            } else if(this.car.getVelocity().len() < TURNING_SPEED){
                return new Action(true, false, LEFT_TURN);
            } else {
                return new Action(false, false, LEFT_TURN);
            }
        } else {
            if(this.car.getVelocity().len() > TURNING_SPEED){
                return new Action(false, true, -TURNING_RATE);
            } else if(this.car.getVelocity().len() < TURNING_SPEED){
                return new Action(true, false, -TURNING_RATE);
            } else {
                return new Action(false, false, -TURNING_RATE);
            }
        }
    }

}
