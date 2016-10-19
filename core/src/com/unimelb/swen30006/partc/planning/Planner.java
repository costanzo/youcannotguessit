package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.partc.ai.interfaces.IPlanning;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;

import java.awt.geom.Point2D;

/**
 * The Planner class is in charge of planning the route, control the movement of car
 * and estimate the time left to the destination.
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

        //the initial state of the car should be straight
        this.state = new CarState(CarState.State.STRAIGHT, 0, 0);

        this.gps = new Navigation(car, this.state, dest);
    }

    /**
     * This method will plan the route and give the route to the Navigation
     * @param destination The point we want to reach
     * @return if the destination can be reached, return true; otherwise return false.
     */
    public boolean planRoute(Point2D.Double destination){
        Route route = routePlanner.getRoute(car.getPosition(), destination);
        gps.setRoute(route);
        return route != null;
    }

    public void update(PerceptionResponse[] results, int visibility, float delta){
        if(!gps.readyToGo()) {
            //the route is not planned, car cannot go
            planRoute(this.destination);
        } else {
            //update the state of the car via gps
            gps.setState();
            PerceptionResponse pr = this.priorityStrategy.getHighestPriority(results, this.state);
            Action action = this.handlingStrategy.getAction(pr, this.state);

            if(this.car.getColour() == Color.CORAL)
                System.out.println(eta());

            //control the movement of the car
            action.takeAction(this.car);
        }
        this.car.update(delta);
    }

    public float eta(){
        //get eta from gps
        return this.gps.eta();
    }
}
