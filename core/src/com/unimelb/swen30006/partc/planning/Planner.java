package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.core.objects.WorldObject;
import com.unimelb.swen30006.partc.roads.Road;
import com.unimelb.swen30006.partc.tong.Action;
import com.unimelb.swen30006.partc.tong.Navigation;

import java.awt.geom.Point2D;

/**
 * Created by Sean on 9/26/2016.
 */
public class Planner implements IPlanning {

    private RoutePlanner routePlanner;

    private Car car;
    private CarState state;
    private Navigation gps;

    private Point2D.Double destination;
    private PriorityStrategy priorityStrategy;
    private HandlingStrategy handlingStrategy;

    public Planner(Car car, Point2D.Double dest, Map map){
        this.car = car;
        this.destination = dest;
        this.routePlanner = new SimpleRoutePlanner(map);
        this.priorityStrategy = new SimplePriorityStrategy();
        this.handlingStrategy = new SimpleHandlingStrategy();
        this.state = new CarState(CarState.State.STRAIGHT, 0, 0);
        this.gps = new Navigation(car, this.state, dest);
    }

    public boolean planRoute(Point2D.Double destination){
        Route route = routePlanner.getRoute(car.getPosition(), destination);
        gps.setRoute(route);
        if(route == null){
            return false;
        } else {
            return true;
        }
    }

    public void update(PerceptionResponse[] results, int visibility, float delta){
        if(!gps.readyToGo()) {
            planRoute(this.destination);
        } else {
            gps.setState();
            //System.out.println(this.car.getVelocity().len());
            PerceptionResponse pr = this.priorityStrategy.getHighesPriority(results, this.state);
            //System.out.println(pr);
            Action action = this.handlingStrategy.getAction(pr, this.state);
            action.takeAction(this.car);
        }
        this.car.update(delta);
    }

    public float eta(){
        return 0;
    }
}
