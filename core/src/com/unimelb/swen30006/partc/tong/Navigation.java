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
    private CarState state;
    private Car car;
    private Route route;
    private Map map;
    private Road nextRoad;
    private float rotation_goal;



    public Navigation(Car car, Map map, CarState state){
        this.currentRoad=map.findRoad(car.getPosition());
        this.currentIntersection = map.findIntersection(car.getPosition());
        this.car = car;
        this.state = state;
        this.map = map;
        this.route = null;
    }

    public void setRoute(Route route){
        this.route = route;
    }


    public CarState getState(){
        if(!onCurrentRoad()&&!onNextRoad()) {
            state=getNextState();
        }else{
            state.setState(CarState.State.STRAIGHT);
            state.setAngle(car.getAngleDifference());
            state.setShift(currentRoad.minDistanceTo(car.getPosition()));
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


//    based on the current road and next road, get the new state.
    private CarState getNextState(){
        nextRoad = route.nextRoad(currentRoad);
        Intersection.Direction next_road_direction = map.findTurningDirection(currentRoad,nextRoad);
        Intersection.Direction moving_direction = car.getMovingDirection();
        state.setShift(nextRoad.minDistanceTo(car.getPosition()));
        float adjustrotation = car.adjustrotation();
        if(moving_direction== Intersection.Direction.North){
            if(next_road_direction== Intersection.Direction.West){
                rotation_goal=180;
                state.setAngle(adjustrotation-rotation_goal);
                state.setState(CarState.State.LEFT);
            }else if (next_road_direction == Intersection.Direction.East){
                rotation_goal=0;
                state.setAngle(adjustrotation);
                state.setState(CarState.State.RIGHT);
            }
        }else if (moving_direction== Intersection.Direction.South){
            if(next_road_direction== Intersection.Direction.West){
                rotation_goal=-180;
                state.setAngle(adjustrotation-rotation_goal);
                state.setState(CarState.State.RIGHT);
            }else if (next_road_direction == Intersection.Direction.East){
                rotation_goal=0;
                state.setAngle(adjustrotation);
                state.setState(CarState.State.LEFT);
            }
        }else if (moving_direction== Intersection.Direction.West){
            if(next_road_direction== Intersection.Direction.North){
                rotation_goal=90;
                state.setAngle(rotation_goal-adjustrotation);
                state.setState(CarState.State.RIGHT);
            }else if (next_road_direction== Intersection.Direction.South){
                rotation_goal=-90;
                state.setAngle(adjustrotation-rotation_goal);
                state.setState(CarState.State.LEFT);
            }
        }else if (moving_direction== Intersection.Direction.East) {
            if (next_road_direction == Intersection.Direction.North) {

                rotation_goal = 90;
                state.setAngle(adjustrotation-rotation_goal);
                state.setState(CarState.State.LEFT);
            } else if (next_road_direction == Intersection.Direction.South) {
                rotation_goal = -90;
                state.setAngle(adjustrotation-rotation_goal);
                state.setState(CarState.State.RIGHT);
            }
        }else{
            state.setAngle(adjustrotation);
            state.setState(CarState.State.STRAIGHT);

        }
        return state;
    }


}

