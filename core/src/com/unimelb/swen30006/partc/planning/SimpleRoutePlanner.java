package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Sean on 9/30/2016.
 */
public class SimpleRoutePlanner implements RoutePlanner {
    private Point2D.Double destination;
    private Map map;

    public SimpleRoutePlanner(Map map){
        this.map = map;
    }

    public Route getRoute(Point2D.Double departurePosition, Point2D.Double dest){
        this.destination = dest;
        Road currentRoad = map.findRoad(departurePosition);
        Road destinationRoad = map.findRoad(this.destination);
        if(destinationRoad == null){
            return null;
        }

        Route route = calculateBestRoute(currentRoad, destinationRoad);
        return route;
    }

    private Route calculateBestRoute(Road start, Road end){
        ArrayList<Road> rs = new ArrayList<Road>();
        ArrayList<Intersection> is = new ArrayList<Intersection>();


        Road road = start;
        Intersection intersection;
        while(!road.equals(end)){
            rs.add(road);
            Intersection[] roadIntersections = map.getRoadIntersections(road);
            intersection = findCloserInteraction(roadIntersections, end);
            is.add(intersection);
            road = findClosestRoad(intersection, end);
        }

        rs.add(road);

        return new Route(rs.toArray(new Road[0]), is.toArray(new Intersection[0]));

    }

    private Intersection findCloserInteraction(Intersection[] is, Road dest){
        Intersection closest = null;
        float minDist = Integer.MAX_VALUE;
        for(Intersection i : is){
            float dist = dest.minDistanceTo(i.pos);
            if(dist < minDist){
                minDist = dist;
                closest = i;
            }
        }

        return closest;
    }

    private Road findClosestRoad(Intersection i, Road dest){
        Intersection.Direction[] dirs = Intersection.Direction.values();
        ArrayList<Road> rs = new ArrayList<Road>(4);
        for(Intersection.Direction d : dirs){
            Road r = i.roads.get(d);
            if(r.equals(dest)){
                return dest;
            }
            rs.add(r);
        }

        return closestRoad(rs, dest);

    }

    private Road closestRoad(ArrayList<Road> rs, Road dest){
        Road closest = null;
        float minDist = Integer.MAX_VALUE;
        for(Road r : rs){
            //float dist = roadDistance(r, dest);
            float dist = r.minDistanceTo(this.destination);
            if(dist < minDist){
                closest = r;
                minDist = dist;
            }
        }
        return closest;
    }

}
