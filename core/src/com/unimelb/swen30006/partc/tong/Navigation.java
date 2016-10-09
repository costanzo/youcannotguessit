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

    private Road previousRoad;
    private Road currentRoad;
    private CarState state;
    private Car car;
    private Route route;
    private Map map;
    private Road nextRoad;
    private float rotation_goal;



    public Navigation(Car car, Map map, CarState state){
        this.car = car;
        this.state = state;
        this.map = map;
        this.route = null;
        this.currentRoad = map.findRoad(car.getPosition());
    }

    public void setRoute(Route route){
        this.route = route;
    }


    public CarState getState(){
        if(currentRoad!=null){
            previousRoad = currentRoad;
        }
        this.currentRoad=route.findCurrentRoad(car.getPosition());
        if(currentRoad==null) {
            this.nextRoad = route.nextRoad(previousRoad);
            state=getNextState();
        }else{
            nextRoad = null;
            state.setState(CarState.State.STRAIGHT);
            state.setAngle(getAngleDifference(currentRoad));
            state.setShift(get_shift(map.findRoad(this.car.getPosition())));
        }
        return state;
    }



//    based on the current road and next road, get the new state.
    private CarState getNextState(){
        Intersection.Direction next_road_direction = map.findTurningDirection(previousRoad,nextRoad);
        Intersection.Direction moving_direction = car.getMovingDirection();
        state.setShift(get_shift(nextRoad));
        float adjustRotation = car.adjustrotation();
        if(moving_direction== Intersection.Direction.North){
            if(next_road_direction== Intersection.Direction.West){
                rotation_goal=180;
                state.setAngle(adjustRotation-rotation_goal);
                state.setState(CarState.State.LEFT);
            }else if (next_road_direction == Intersection.Direction.East){
                rotation_goal=0;
                state.setAngle(adjustRotation);
                state.setState(CarState.State.RIGHT);
            }
        }else if (moving_direction== Intersection.Direction.South){
            if(next_road_direction== Intersection.Direction.West){
                rotation_goal=-180;
                state.setAngle(adjustRotation-rotation_goal);
                state.setState(CarState.State.RIGHT);
            }else if (next_road_direction == Intersection.Direction.East){
                rotation_goal=0;
                state.setAngle(adjustRotation);
                state.setState(CarState.State.LEFT);
            }
        }else if (moving_direction== Intersection.Direction.West){
            if(next_road_direction== Intersection.Direction.North){
                rotation_goal=90;
                state.setAngle(rotation_goal-adjustRotation);
                state.setState(CarState.State.RIGHT);
            }else if (next_road_direction== Intersection.Direction.South){
                rotation_goal=-90;
                state.setAngle(adjustRotation-rotation_goal);
                state.setState(CarState.State.LEFT);
            }
        }else if (moving_direction== Intersection.Direction.East) {
            if (next_road_direction == Intersection.Direction.North) {
                rotation_goal = 90;
                state.setAngle(adjustRotation-rotation_goal);
                state.setState(CarState.State.LEFT);
            } else if (next_road_direction == Intersection.Direction.South) {
                rotation_goal = -90;
                state.setAngle(adjustRotation-rotation_goal);
                state.setState(CarState.State.RIGHT);
            }
        }else{
            state.setAngle(adjustRotation);
            state.setState(CarState.State.STRAIGHT);

        }
        return state;
    }

    private float get_shift(Road currentRoad){
        if(currentRoad.getEndPos().getY()==currentRoad.getStartPos().getY()){
            float midpoint = (float)currentRoad.getStartPos().getY();
            if(this.car.adjustrotation()<=90&&this.car.adjustrotation()>=-90){

                return ((float)this.car.getPosition().getY()-midpoint);
            }else{
                return (midpoint-(float)this.car.getPosition().getY());
            }
        }else{
            float midpoint = (float) currentRoad.getStartPos().getX();
            if(this.car.adjustrotation()>=0&&this.car.adjustrotation()<=180){
                return (midpoint-(float)this.car.getPosition().getX());
            }else{
                return ((float)this.car.getPosition().getX()-midpoint);
            }
        }
    }
    //calculate the angle difference between the angle the car towards and the angle of it should be
    private float getAngleDifference(Road road){
        float adjustRotation = car.adjustrotation();
        if(road.getStartPos().getX()==road.getEndPos().getX()){
            return (90-adjustRotation);
        }else{
            return adjustRotation;
        }
    }

}

