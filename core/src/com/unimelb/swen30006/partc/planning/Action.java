package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.controllers.KeyboardController;
import com.unimelb.swen30006.partc.core.objects.Car;

/**
 * Created by tong on 16-10-1.
 */
public class Action {
    public final float angle;
    public final boolean accelerate;
    public final boolean brake;

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

}
