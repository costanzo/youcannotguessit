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
    private Road[] roads;
    private Intersection[] intersections;


    public Route(Road[] roads, Intersection[] intersections){
        this.roads = roads;
        this.intersections = intersections;

    }

    public Road[] getRoads() {
        return roads;

    }

    public Intersection[] getIntersections(){
        return intersections;
    }

    public Road nextRoad(Road currentRoad){
        int i = getRoadIndex(currentRoad);
        if( i == (roads.length-1) ){
            return null;
        } else if( i != -1){
            return roads[i+1];
        }
        else {
            // cannot find a road
            return null;
        }
    }

    public Intersection nextIntersection(Road currentRoad){
        int i = getRoadIndex(currentRoad);

        if ( i == (roads.length - 1)){
            return null;
        } else if( i != -1){
            return intersections[i];
        } else {
            // cannot find the road
            return null;
        }
    }

    public int getIntersectionIndex(Intersection intersection){
        int i;
        for(i=0;i<intersection.length;i++){
            if(intersections[i].pos==intersection.pos){
                return i;
            }
        }
        return -1;
    }

    public int getIntersectionLength(){
        return  intersections.length;
    }

    public Intersection getIntersectionByIndex(int i){
        return intersections[i];
    }

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

    public Road findCurrentRoad(Point2D.Double pos){
        for(Road r : roads){
            if (r.containsPoint(pos)){
                return r;
            }
        }
        return null;
    }

    public Intersection findCurrentIntersection(Point2D.Double pos){
        for(Intersection i : intersections){
            if (i.containsPoint(pos)){
                return i;
            }
        }
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

    public Intersection.Direction getTurnDirection(Road exit, Road nextEntry){
        Intersection inte = nextIntersection(exit);

        Intersection.Direction[] dirs = Intersection.Direction.values();
        for(Intersection.Direction d : dirs){
            Road r = inte.roads.get(d);
            if(r.equals(nextEntry)){
                if(d == Intersection.Direction.East){
                    return Intersection.Direction.West;
                } else if(d == Intersection.Direction.West){
                    return Intersection.Direction.East;
                } else{
                    return d;
                }
            }
        }

        return null;
    }

}
