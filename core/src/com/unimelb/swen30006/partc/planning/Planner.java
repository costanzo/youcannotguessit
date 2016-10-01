package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.tong.Navigation;

import java.awt.geom.Point2D;

/**
 * Created by Sean on 9/26/2016.
 */
public class Planner implements IPlanning {

    private RoutePlanner routePlanner;

    private Car car;
    private int times = 0;
    private Route route;
    private Point2D.Double destination;
    private PriorityStrategy priorityStrategy;

    public Planner(Car car, Point2D.Double dest, Map map){
        this.car = car;
        this.routePlanner = new SimpleRoutePlanner(dest, map);
        this.priorityStrategy = new SimplePriorityStrategy(car);
    }

    public boolean planRoute(Point2D.Double destination){
        this.route = routePlanner.getRoute(car.getPosition());
        if(route == null){
            return false;
        } else {
            return true;
        }
    }

    public void update(PerceptionResponse[] results, int visibility, float delta){
        if(this.route == null) {
            planRoute(this.destination);
        }

        priorityStrategy.getHighesPriority(results, Navigation.CarState.STRAIGHT);

        this.car.update(delta);
    }

    public float eta(){
        return 0;
    }
}
