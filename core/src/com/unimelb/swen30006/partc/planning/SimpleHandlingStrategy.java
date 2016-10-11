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
    public static final float TURNING_SPEED = 10f;

    public static final float TURNING_RATE = 0.3f;
    public static final float LEFT_TURN = 2f;
    public static final float RIGHT_TURN = 0.6f;

    public static final float LANE_MARGIN = 5f;
    public static final float ADJUST_TURNING_THRES = 0.5f;

    public static final float ADJUST_COEFF = 4f;
    public static final float TURNING_COEFF = 10f;

    public SimpleHandlingStrategy(){
    }

    public Action getAction(PerceptionResponse perceptionResponse, CarState state){
        //float turningAngle = adjustPosture(state.angle, state.shift);
        float turningAngle = state.getAngle();
        Vector2 velo = state.getVelocity();
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
                    return new Action(true, false, turn * Math.abs(turningAngle)/TURNING_COEFF);
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
                if(perceptionResponse.information.get("state") != Color.GREEN){
                    return new Action(false, true, turn);
                } else {
                    if(velo.len() > TURNING_SPEED){
                        return new Action(false, true, turn);
                    } else{
                        return new Action(true, false, turn);
                    }
                }
            } else {
                return new Action(false, true, turn);

            }
        } else if(st == CarState.State.LEFT){
            if(velo.len() > TURNING_SPEED){
                return new Action(false, true, LEFT_TURN);
            } else if(velo.len() < TURNING_SPEED){
                return new Action(true, false, LEFT_TURN);
            } else {
                return new Action(false, false, LEFT_TURN);
            }
        } else {
            if(velo.len() > TURNING_SPEED){
                return new Action(false, true, -RIGHT_TURN);
            } else if(velo.len() < TURNING_SPEED){
                return new Action(true, false, -RIGHT_TURN);
            } else {
                return new Action(false, false, -RIGHT_TURN);
            }
        }
    }

}
