package com.unimelb.swen30006.partc.tong;

import com.unimelb.swen30006.partc.controllers.KeyboardController;
import com.unimelb.swen30006.partc.core.objects.Car;

/**
 * Created by tong on 16-10-1.
 */
public class Action {
    Navigation.CarState state;
    Car car;
    float goal_angle;
    KeyboardController keyboard;

    Action(Car car){
        this.car = car;
    }

    public void take_action(Navigation.CarState carState,float delta){
        this.state = carState;
        float current_angle = car.getRotation();
        this.car.accelerate();
        if(state== Navigation.CarState.STRAIGHT){
            return;
        }else if(state== Navigation.CarState.LEFT){

        }

    }


}
