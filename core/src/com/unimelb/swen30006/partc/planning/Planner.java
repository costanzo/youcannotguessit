package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
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
        this.handlingStrategy = new SimpleHandlingStrategy();
        this.gps = new Navigation(car, map);
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

        this.state = gps.getState();
        PerceptionResponse pr = this.priorityStrategy.getHighesPriority(results, this.state);
        System.out.println(pr);
        //Action action = this.handlingStrategy.getAction(pr,this.state);
        //action.takeAction(this.car);

        this.car.update(delta);
    }

    public float eta(){
        return 0;
    }
}
