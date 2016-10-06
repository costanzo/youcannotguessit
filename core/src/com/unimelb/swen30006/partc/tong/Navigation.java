package com.unimelb.swen30006.partc.tong;

import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.planning.CarState;
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
    private float rotation_goal;



    public Navigation(Car car, Map map){
        this.currentRoad=map.findRoad(car.getPosition());
        this.currentIntersection = map.findIntersection(car.getPosition());
        this.state = getState();
        this.car = car;
        this.map = map;
        this.route = null;
    }

    public void setRoute(Route route){
        this.route = route;
    }

    public CarState getState(){
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

    public float getRotation_goal(){
        return rotation_goal;
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
                rotation_goal=180;
                return CarState.LEFT;
            }else if (next_road_direction == Intersection.Direction.East){
                rotation_goal=0;
                return CarState.RIGHT;
            }
        }else if (moving_direction== Intersection.Direction.South){
            if(next_road_direction== Intersection.Direction.West){
                rotation_goal=180;
                return CarState.RIGHT;
            }else if (next_road_direction == Intersection.Direction.East){
                rotation_goal=0;
                return CarState.LEFT;
            }
        }else if (moving_direction== Intersection.Direction.West){
            if(next_road_direction== Intersection.Direction.North){
                rotation_goal=90;
                return CarState.RIGHT;
            }else if (next_road_direction== Intersection.Direction.South){
                rotation_goal=-90;
                return CarState.LEFT;
            }
        }else if (moving_direction== Intersection.Direction.East){
            if(next_road_direction== Intersection.Direction.North){
                rotation_goal=0;
                return CarState.LEFT;
            }else if (next_road_direction== Intersection.Direction.South){
                rotation_goal=-90;
                return CarState.RIGHT;
            }
        }
        return CarState.STRAIGHT;
    }
}
