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

    public Map(Road[] roads, Intersection[] intersections) {
        this.roads = roads;
        this.intersections = intersections;
    }

    public Road findRoad(Point2D.Double pos){
        for(Road r : roads){
            if (r.containsPoint(pos)){
                return r;
            }
        }
        return null;
    }

    public Intersection findIntersection(Point2D.Double pos){
        for(Intersection i : intersections){
            if ( i.containsPoint(pos)){
                return i;
            }
        }

        return null;
    }

    public Intersection findRoadsInteraction(Road road1, Road road2){
        Point2D.Double road1Start = road1.getStartPos();
        Point2D.Double road1End   = road1.getEndPos();
        Point2D.Double road2Start = road2.getStartPos();
        Point2D.Double road2End   = road2.getEndPos();

        Point2D.Double i1;
        Point2D.Double i2;

        if(road1Start.distance(road2Start) < 50f){
            i1 = road1Start;
            i2 = road2Start;
        } else if(road1Start.distance(road1End) < 50f){
            i1 = road1Start;
            i2 = road2End;
        } else if(road1End.distance(road2Start) < 50f){
            i1 = road1End;
            i2 = road2Start;
        } else {
            i1 = road1End;
            i2 = road2End;
        }

        for(Intersection intersection : intersections){
            if(i1.distance(intersection.pos)<40f){
                return intersection;
            }
        }

        return null;
    }

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
