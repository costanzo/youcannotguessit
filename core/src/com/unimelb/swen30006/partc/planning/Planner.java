package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;

import java.awt.geom.Point2D;

/**
 * Created by Sean on 9/26/2016.
 */
public class Planner implements IPlanning {
    public enum State{
        GO_STRAIGHT,
        TURN_LEFT,
        TURN_RIGHT
    }

    private RoutePlanner routePlanner;

    private Car car;
    private int times = 0;
    private Route route;
    private Point2D.Double destination;

    public Planner(Car car, Point2D.Double dest, Map map){
        this.car = car;
        this.routePlanner = new SimpleRoutePlanner(dest, map);
    }

    public boolean planRoute(Point2D.Double destination){
        this.route = routePlanner.getRoute(car.getPosition());
        if(route == null){
            return false;
        } else {
            System.out.println(route);
            return true;
        }
    }

    public void update(PerceptionResponse[] results, int visibility, float delta){
        if(this.route == null) {
            planRoute(this.destination);
        }

        this.car.update(delta);
    }

    public float eta(){
        return 0;
    }
}
