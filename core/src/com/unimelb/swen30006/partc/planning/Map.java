package com.unimelb.swen30006.partc.planning;


import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Sean on 10/1/2016.
 */
public class Map {
    private Road[] roads;
    private Intersection[] intersections;

    /**
     *
     * @param roads: all the roads are read from xml
     * @param intersections: all the intersections are read from xml
     */
    public Map(Road[] roads, Intersection[] intersections) {
        this.roads = roads;
        this.intersections = intersections;
    }

    /**
     *
     * @param pos: the current position in double type
     * @return: the road that contains the point
     */
    public Road findRoad(Point2D.Double pos){
        for(Road r : roads){
            if (r.containsPoint(pos)){
                return r;
            }
        }
        return null;
    }


    /**
     *
     * @param road: an instance of Road
     * @return: get the intersections connecting to the road
     */
    public Intersection[] getRoadIntersections(Road road){
        ArrayList<Intersection> is = new ArrayList<Intersection>(2);
        for(Intersection i : intersections){
            Intersection.Direction[] dirs = Intersection.Direction.values();
            for(Intersection.Direction d : dirs){
                Road r = i.roads.get(d);
                if(r != null && r.equals(road)){
                    is.add(i);
                }
            }
        }

        return is.toArray(new Intersection[0]);
    }
}
