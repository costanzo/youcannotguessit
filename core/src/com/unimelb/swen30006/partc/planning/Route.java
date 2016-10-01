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
}
