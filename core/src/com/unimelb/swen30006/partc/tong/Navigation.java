package com.unimelb.swen30006.partc.tong;

import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.planning.Map;
import com.unimelb.swen30006.partc.planning.Route;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

/**
 * Created by tong on 16-10-1.
 */


public class Navigation {

    private Road currentRoad;
    private Intersection currentIntersection;
    private CarState state = null;
    private Car car;
    private Route route;
    private Map map;
    private Road nextRoad;

    public enum CarState{
        LEFT,RIGHT,STRAIGHT
    }

    Navigation(Car car, Route route, Map map){
        this.currentRoad=map.findRoad(car.getPosition());
        this.currentIntersection = map.findIntersection(car.getPosition());
        this.state = getState();
        this.car = car;
        this.route = route;
        this.map = map;
    }

    private CarState getState(){
        if(!onCurrentRoad()&&!onNextRoad()) {
            state=getNextState();
        }
        return state;
    }

    //check the car is on the current road
    private boolean onCurrentRoad(){
        if(currentRoad.containsPoint(car.getPosition())){
            return true;
        }else {
            return false;
        }
    }
    //check the car is on the next road
    private boolean onNextRoad(){
        if(nextRoad!=null){
            if(nextRoad.containsPoint(car.getPosition())) return true;
        }
        return false;

    }

    //based on the current road and next road, get the new state.
    private CarState getNextState(){
        nextRoad = route.nextRoad(currentRoad);
        Intersection.Direction next_road_direction = map.findTurningDirection(currentRoad,nextRoad);
        Intersection.Direction moving_direction = car.getMovingDirection();
        currentRoad = nextRoad;
        nextRoad=route.nextRoad(currentRoad);
        if(moving_direction== Intersection.Direction.North){
            if(next_road_direction== Intersection.Direction.West){
                return CarState.LEFT;
            }else if (next_road_direction == Intersection.Direction.East){
                return CarState.RIGHT;
            }
        }else if (moving_direction== Intersection.Direction.South){
            if(next_road_direction== Intersection.Direction.West){
                return CarState.RIGHT;
            }else if (next_road_direction == Intersection.Direction.East){
                return CarState.LEFT;
            }
        }else if (moving_direction== Intersection.Direction.West){
            if(next_road_direction== Intersection.Direction.North){
                return CarState.RIGHT;
            }else if (next_road_direction== Intersection.Direction.South){
                return CarState.LEFT;
            }
        }else if (moving_direction== Intersection.Direction.East){
            if(next_road_direction== Intersection.Direction.North){
                return CarState.LEFT;
            }else if (next_road_direction== Intersection.Direction.South){
                return CarState.RIGHT;
            }
        }
        return CarState.STRAIGHT;
    }
}
