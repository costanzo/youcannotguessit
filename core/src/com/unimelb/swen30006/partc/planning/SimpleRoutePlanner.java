package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

import java.awt.geom.Point2D;

/**
 * Created by Sean on 9/30/2016.
 */
public class SimpleRoutePlanner implements RoutePlanner {
    private Point2D.Double destination;
    private Road[] roads;
    private Intersection[] intersections;

    public SimpleRoutePlanner(Point2D.Double destination, Road[] roads){
        this.destination = destination;
        this.roads = roads;
    }

    public Route getRoute(Point2D.Double departurePosition){
        Road currentRoad = findRoad(departurePosition);
        Road destinationRoad = findRoad(this.destination);

        if(destinationRoad == null){
            return null;
        }

        Route route = calculateBestRoute(currentRoad, destinationRoad);
        return route;
    }

    private Road findRoad(Point2D.Double pos){
        for(Road r : roads){
            if (r.containsPoint(pos))
                return r;
        }
        return null;
    }

    private Route calculateBestRoute(Road start, Road end){
        return null;
    }
}
