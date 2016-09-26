package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;

import java.awt.geom.Point2D;

/**
 * Created by Sean on 9/26/2016.
 */
public class Planner implements IPlanning {
    private Car car;
    private int times = 0;

    public Planner(Car car){
        this.car = car;
    }

    public boolean planRoute(Point2D.Double destination){
        return true;
    }

    public void update(PerceptionResponse[] results, int visibility, float delta){
        if(times <= 5) {
            this.car.accelerate();
            times ++;
        }

        this.car.update(delta);
    }

    public float eta(){
        return 0;
    }
}
