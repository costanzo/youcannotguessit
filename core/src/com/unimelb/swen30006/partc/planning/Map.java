package com.unimelb.swen30006.partc.planning;

import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

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
}
