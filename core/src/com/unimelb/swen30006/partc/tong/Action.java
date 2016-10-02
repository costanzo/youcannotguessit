package com.unimelb.swen30006.partc.tong;

import com.unimelb.swen30006.partc.controllers.KeyboardController;
import com.unimelb.swen30006.partc.core.objects.Car;

/**
 * Created by tong on 16-10-1.
 */
public class Action {
    private final static float ROTATION_RATE = 150f;
    Navigation.CarState state;
    Car car;
    float goal_angle;
    KeyboardController keyboard;
    Navigation navigation;

    Action(Car car,Navigation navigation){
        this.car = car;
        this.navigation = navigation;
    }

    public void take_action(Navigation.CarState carState,float delta){
        this.state = carState;
        this.car.accelerate();
        this.goal_angle = navigation.getRotation_goal();
        car.accelerate();
        rotate(car,delta,goal_angle);
    }


    public void rotate (Car car, float delta, float goal_angle){
        float current_rotation = car.getRotation();
        float difference = current_rotation-goal_angle;

        if(difference>ROTATION_RATE*delta){
            car.turn(ROTATION_RATE*delta);
        }else if (difference<ROTATION_RATE*delta){
            car.turn(-ROTATION_RATE*delta);
        }else{
            car.turn(difference);
        }

    }

}
