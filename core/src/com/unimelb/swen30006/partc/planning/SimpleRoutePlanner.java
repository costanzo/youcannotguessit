package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * This class will generate the best route for the start position and end position
 */
public class SimpleRoutePlanner implements RoutePlanner {
    private Point2D.Double destination;
    private Map map;

    public SimpleRoutePlanner(Map map){
        this.map = map;
    }

    /**
     * This method plan the route for the car based on the departure position and destination
     * @param departurePosition departure position
     * @param dest destination postion
     * @return the route that the car should passes through
     */
    public Route getRoute(Point2D.Double departurePosition, Point2D.Double dest){
        this.destination = dest;

        //the road that the start position is in
        Road currentRoad = map.findRoad(departurePosition);

        //the destination road
        Road destinationRoad = map.findRoad(this.destination);

        Route route = null;
        if(destinationRoad != null){
            //get the best road
            route = calculateBestRoute(currentRoad, destinationRoad);
        }
        return route;
    }

    //get the route for car to run from start road to end road
    private Route calculateBestRoute(Road start, Road end){
        ArrayList<Road> rs = new ArrayList<Road>();
        ArrayList<Intersection> is = new ArrayList<Intersection>();
        Road road = start;
        Intersection intersection;
        while(!road.equals(end)){
            rs.add(road);
            //the intersection connected to this road
            Intersection[] roadIntersections = map.getRoadIntersections(road);

            //the intersection that is close to the destination
            intersection = findCloserInteraction(roadIntersections, end);
            is.add(intersection);

            //find the closest road connected to the intersection to the end road
            road = findClosestRoad(intersection, end);
        }

        rs.add(road);

        return new Route(rs.toArray(new Road[0]), is.toArray(new Intersection[0]));
    }

    //get the intersection from the intersections that is closest to the destination road
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

    //get the road that is connected to the intersection i that is closest to the destination road
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

        return closestRoad(rs);

    }

    //get the road from the road list that is the closest to the destination position
    private Road closestRoad(ArrayList<Road> rs){
        Road closest = null;
        float minDist = Integer.MAX_VALUE;
        for(Road r : rs){
            float dist = r.minDistanceTo(this.destination);
            if(dist < minDist){
                closest = r;
                minDist = dist;
            }
        }
        return closest;
    }
}
