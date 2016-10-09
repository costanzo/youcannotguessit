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

    private Route route;
    private Point2D.Double destination;
    private PriorityStrategy priorityStrategy;
    private HandlingStrategy handlingStrategy;

    public Planner(Car car, Point2D.Double dest, Map map){
        this.car = car;
        this.routePlanner = new SimpleRoutePlanner(dest, map);
        this.priorityStrategy = new SimplePriorityStrategy(car);
        this.handlingStrategy = new SimpleHandlingStrategy(car);
        this.state = new CarState(CarState.State.STRAIGHT, 0, 0);
        this.gps = new Navigation(car, map, this.state);
    }

    public boolean planRoute(Point2D.Double destination){
        this.route = routePlanner.getRoute(car.getPosition());
        gps.setRoute(this.route);
        if(this.route == null){
            return false;
        } else {
            return true;
        }
    }

    public void update(PerceptionResponse[] results, int visibility, float delta){
        if(this.route == null) {
            planRoute(this.destination);
        }

        gps.setState();
        //System.out.println(this.state);
        PerceptionResponse pr = this.priorityStrategy.getHighesPriority(results, new CarState(CarState.State.STRAIGHT, 0, 0));
        Action action = this.handlingStrategy.getAction(pr,this.state);
        System.out.print(car.getVelocity().len());
        System.out.println(action);
        action.takeAction(this.car);

        this.car.update(delta);
    }

    public float eta(){
        return 0;
    }
}
