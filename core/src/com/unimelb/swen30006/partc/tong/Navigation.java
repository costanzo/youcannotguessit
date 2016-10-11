package com.unimelb.swen30006.partc.tong;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.planning.CarState;
import com.unimelb.swen30006.partc.planning.Map;
import com.unimelb.swen30006.partc.planning.Route;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

import java.awt.geom.Point2D;

/**
 * Created by tong on 16-10-1.
 */


public class Navigation {
    public static float DEST_DISTANCE = 30f;

    private Road previousRoad;
    private Road currentRoad;
    private CarState state;
    private Car car;
    private Route route;
    private Road nextRoad;
    private float rotation_goal;
    private Point2D.Double dest;


    public Navigation(Car car, CarState state, Point2D.Double dest){
        this.car = car;
        this.state = state;
        this.route = null;
        this.dest = dest;
    }

    public void setRoute(Route route){
        this.route = route;
    }

    public void setState(){
        this.state.setPos(this.car.getPosition());
        this.state.setRotation(this.car.getRotation());
        this.state.setVelocity(this.car.getVelocity());

        if(currentRoad!=null){
            previousRoad = currentRoad;
        }
        this.currentRoad=route.findCurrentRoad(car.getPosition());
        Intersection its = route.findCurrentIntersection(car.getPosition());
        if(its != null){
            this.currentRoad = null;
        }
        if(currentRoad ==null) {
            this.nextRoad = route.nextRoad(previousRoad);
            setNextState();
        }else{
            setStateOnRoad();
        }
        System.out.println(this.est());

    }

    private void setStateOnRoad(){
        if(reachDest()){
            this.state.setState(CarState.State.REACH_DEST);
            return;
        }

        Vector2 rotatedCarDir = null;
        float x = this.state.getDirection().x;
        float y = this.state.getDirection().y;
        double shift = 0;
        if(this.currentRoad.getStartPos().getX() == this.currentRoad.getEndPos().getX()) {
            //vertical road
            double mid = this.currentRoad.getStartPos().getX();
            shift = this.car.getPosition().getX() - mid;
            if (shift < 0) {
                //rotate clock-wise 90 degree
                rotatedCarDir = new Vector2(y, 0-x);
                shift = 0-shift;
            } else {
                //rotate clock-wise 270 degree
                rotatedCarDir = new Vector2(0-y, x);
            }
        } else{
            //horizonal road
            double mid = this.currentRoad.getStartPos().getY();
            shift = this.car.getPosition().getY() - mid;
            if(shift > 0){
                //don't need to rotate
                rotatedCarDir = new Vector2(x, y);
            } else{
                //rotate clock-wise 180 degree
                rotatedCarDir = new Vector2(0-x, 0-y);
                shift = 0 - shift;
            }
        }

        double angle = 0;
        x = rotatedCarDir.x;
        y = rotatedCarDir.y;

        if(x >= 0 && y >= 0){
            //First quadrant
             angle = Math.asin(y) * 180/Math.PI;
        } else if( x < 0 && y >= 0){
            //second quadrant
            angle = 180 - Math.asin(y) * 180/Math.PI;
        } else if( x >= 0 && y < 0){
            //fourth quadrant
            angle = Math.asin(y) * 180/Math.PI;
        } else{
            //third quadrant
            angle = 0 - 180 - Math.asin(y) * 180/Math.PI;
        }

        this.state.setAngle((float)angle);
        this.state.setShift((float)shift);
        this.state.setState(CarState.State.STRAIGHT);
    }

    private boolean reachDest(){
        if(this.dest.distance(car.getPosition()) < DEST_DISTANCE){
            return true;
        }
        return false;
    }

    public boolean readyToGo(){
        return this.route != null;
    }

//    based on the current road and next road, get the new state.
    private void setNextState(){
        Intersection.Direction next_road_direction = route.getTurnDirection(previousRoad,nextRoad);
        Intersection.Direction moving_direction = car.getMovingDirection();
        state.setShift(get_shift(nextRoad));

        float adjustRotation = car.adjustrotation();
        if(moving_direction == next_road_direction){
            if(moving_direction == Intersection.Direction.South)
                state.setAngle(90f+adjustRotation);
            else if(moving_direction == Intersection.Direction.North)
                state.setAngle(adjustRotation-90f);
            else if(moving_direction == Intersection.Direction.East)
                state.setAngle(adjustRotation);
            else {
                if (adjustRotation > 0)
                    state.setAngle(180f - adjustRotation);
                else
                    state.setAngle(180f + adjustRotation);
            }
            state.setState(CarState.State.STRAIGHT);
            return ;
        }
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


        }
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

    private float est(){
        float distance = total_distance();
        float speed =10f;

        if(speed!=0){
            return distance/speed;
        }else{
            return -1;
        }
    }
    private float total_distance(){
        if(!reachDest()) {
            float distance = 0;
            Intersection intersection;
            if (currentRoad != null) {
                intersection = route.nextIntersection(currentRoad);
            } else {
                intersection = route.nextIntersection(previousRoad);
            }
            if(route.getIntersectionIndex(intersection)==route.getIntersectionLength()-1){
                distance =(float) (Math.abs (state.getPos().getX()- dest.getX())+Math.abs(state.getPos().getY()-dest.getY()));
                return distance;
            }
            distance += dist_from_point_to_intersection(state.getPos(), intersection);

            for (int i = route.getIntersectionIndex(intersection); i < route.getIntersectionLength() - 1; i++) {
                if (route.getIntersectionByIndex(i).pos.getX() == route.getIntersectionByIndex(i + 1).pos.getX()) {
                    distance += Math.abs(route.getIntersectionByIndex(i).pos.getY() - route.getIntersectionByIndex(i + 1).pos.getY());
                } else {
                    distance += Math.abs(route.getIntersectionByIndex(i).pos.getX() - route.getIntersectionByIndex(i + 1).pos.getX());
                }
            }
            distance += dist_from_point_to_intersection(dest, route.getIntersectionByIndex(route.getIntersectionLength() - 1));
            return distance;
        }else{
            return 0;
        }
    }

    private float dist_from_point_to_intersection(Point2D point,Intersection intersection){
        double distance;
        double x_difference = point.getX()-intersection.pos.getX();
        double y_difference = point.getY()-intersection.pos.getY();
        distance = Math.abs(x_difference)+Math.abs(y_difference);
        return (float) distance;
    }

}

