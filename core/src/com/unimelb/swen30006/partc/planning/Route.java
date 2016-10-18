package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Sean on 9/30/2016.
 */
public class Route {
    //the roads that will be passed through
    private Road[] roads;

    //the intersection that will be passed through
    private Intersection[] intersections;

    public Route(Road[] roads, Intersection[] intersections){
        this.roads = roads;
        this.intersections = intersections;

    }

    public Road[] getRoads() {
        return roads;

    }

    /**
     * This method will find the next road after leaving this road
     * @param currentRoad
     * @return the next road that the car should enter
     */
    public Road nextRoad(Road currentRoad){
        int i = getRoadIndex(currentRoad);
        if( i == (roads.length-1) ){
            //the current road is the last road
            return null;
        } else if( i != -1){
            //last road exists
            return roads[i+1];
        }
        else {
            // cannot find a road
            return null;
        }
    }

    //chech if this intersection is the last intersection
    public boolean isLastIntersection(Intersection intersection){
        if(intersection==null){
            //the car is on the last road and has passed the last intersection
            return true;
        }
        return intersection.equals(intersections[intersections.length-1]);
    }

    //check if this road is the last road in route
    public boolean isLastRoad(Road road){
        return road.equals(roads[roads.length-1]);
    }

    //get the distance from the intersection along the route until the destination
    public float getIntersectionDist(Intersection intersection, Point2D.Double dest){
        double dist = 0;

        //get the distance between every pair of adjacent intersection
        for (int i = getIntersectionIndex(intersection); i < intersections.length - 1; i++) {
            dist += intersections[i].pos.distance(intersections[i+1].pos);
        }

        //last intersection to the destination
        dist += intersections[intersections.length-1].pos.distance(dest);

        return (float)dist;
    }

    //get the next intersection of this road
    public Intersection nextIntersection(Road currentRoad){
        int i = getRoadIndex(currentRoad);

        if ( i == (roads.length - 1)){
            //this is the last road and no intersection following
            return null;
        } else if( i != -1){
            //corresponding intersection
            return intersections[i];
        } else {
            // cannot find the road
            return null;
        }
    }

    //get the index of this intersection in the array
    public int getIntersectionIndex(Intersection intersection){
        int i;
        for(i=0;i<intersection.length;i++){
            if(intersections[i].pos==intersection.pos){
                return i;
            }
        }
        return -1;
    }

    //get the index of the road in the array
    private int getRoadIndex(Road road){
        int i;
        for(i = 0; i< roads.length ; i++){
            if(roads[i].getStartPos().equals(road.getStartPos())&&
                    roads[i].getEndPos().equals(road.getEndPos())){
                return i;
            }
        }
        return -1;
    }

    //find out which road the position is currently in
    public Road findCurrentRoad(Point2D.Double pos){
        for(Road r : roads){
            if (r.containsPoint(pos)){
                return r;
            }
        }
        //not on any road
        return null;
    }

    //find out which intersection the position is currently in
    public Intersection findCurrentIntersection(Point2D.Double pos){
        for(Intersection i : intersections){
            if (i.containsPoint(pos)){
                return i;
            }
        }
        //not on any intersection
        return null;
    }

    public String toString(){
        String out = "";
        for(Road r : this.roads){
            out += r.toString();
            out += "\n";
        }
        for(Intersection i : this.intersections){
            out += i.toString();
            out += "\n";
        }
        return out;
    }

    //find which direction the car should turn from exit road to the next road
    public Intersection.Direction getTurnDirection(Road exit, Road nextEntry){
        Intersection inte = nextIntersection(exit);
        Intersection.Direction[] dirs = Intersection.Direction.values();

        for(Intersection.Direction d : dirs){
            //the road that this intersection connected in direction d
            Road r = inte.roads.get(d);
            if(r.equals(nextEntry)){
                //this road is the next entry
                if(d == Intersection.Direction.East){
                    return Intersection.Direction.West;
                } else if(d == Intersection.Direction.West){
                    return Intersection.Direction.East;
                } else{
                    return d;
                }
            }
        }

        //the next road is not connected to this road
        return null;
    }

}
