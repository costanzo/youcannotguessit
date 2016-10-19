package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.controllers.KeyboardController;
import com.unimelb.swen30006.partc.core.objects.Car;

/**
 * Contains the information of car's next movement.
 */
public class Action {
    public final float angle;
    public final boolean accelerate;
    public final boolean brake;

    /**
     *
     * @param accelerate: a boolean variable determine whether the car need to accelerate
     * @param brake: a boolean variable determine whether the car need to brake
     * @param angle: a float variable describe how much degree the car need to turn
     */
    public Action(boolean accelerate, boolean brake, float angle) {
        this.angle = angle;
        this.accelerate = accelerate;
        this.brake = brake;
    }
    /**
     * control the car based on the variables passed into the class
    **/
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

}
