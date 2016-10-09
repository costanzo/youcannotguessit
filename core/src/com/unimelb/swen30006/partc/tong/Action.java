package com.unimelb.swen30006.partc.tong;

import com.unimelb.swen30006.partc.controllers.KeyboardController;
import com.unimelb.swen30006.partc.core.objects.Car;

/**
 * Created by tong on 16-10-1.
 */
public class Action {
    public final float angle;
    public final boolean accelerate;
    public final boolean brake;

//    private final static float ROTATION_RATE = 150f;
//    Navigation.CarState state;
//    Car car;
//    float goal_angle;
//    KeyboardController keyboard;
//    Navigation navigation;

    public Action(boolean accelerate, boolean brake, float angle) {
        this.angle = angle;
        this.accelerate = accelerate;
        this.brake = brake;
    }

    public void takeAction(Car car){
        if(this.accelerate){
            car.accelerate();
        }
        if(this.brake){
            car.brake();
        }
        car.turn(this.angle);
    }

    public String toString(){
        String out = "";
        if(accelerate){
            out += "Accelerate ";
        }

        if(brake){
            out += " Brake ";
        }

        out += this.angle;

        return out;
    }

//
//    public void rotate (Car car, float delta, float goal_angle){
//        float current_rotation = car.getRotation();
//        float difference = current_rotation-goal_angle;
//
//        if(difference>ROTATION_RATE*delta){
//            car.turn(ROTATION_RATE*delta);
//        }else if (difference<ROTATION_RATE*delta){
//            car.turn(-ROTATION_RATE*delta);
//        }else{
//            car.turn(difference);
//        }
//
//    }

}
