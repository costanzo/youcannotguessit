package com.unimelb.swen30006.partc.planning;


import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * A container contains all the roads and intersections for reducing
 * coupling between World.
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
     * find the road that the position is in or within 50f range
     * @param pos: the current position in double type
     * @return: the nearest road or the road it is current in
     */
    public Road findRoad(Point2D.Double pos){
        Road road = null;
        //try to find the road it is in
        for(Road r : roads){
            if (r.containsPoint(pos)){
                road = r;
            }
        }
        if(road == null){
            //not in the road, find the closest road
            float minDis = Integer.MAX_VALUE;
            for(Road r : roads){
                float dis = r.minDistanceTo(pos);
                if(dis < minDis){
                    road = r;
                    minDis = dis;
                }
            }
            if(road.minDistanceTo(pos) > 50f){
                //too far, out of reach
                road = null;
            }
        }

        return road;
    }


    /**
     * This method tries to find intersections that the road is connected
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
