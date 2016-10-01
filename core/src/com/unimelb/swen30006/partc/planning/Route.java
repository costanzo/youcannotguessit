package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.roads.Intersection;
import com.unimelb.swen30006.partc.roads.Road;

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
        int i;
        for(i = 0; i< roads.length ; i++){
            if(roads[i].getStartPos().equals(currentRoad.getStartPos())&&
                    roads[i].getEndPos().equals(currentRoad.getEndPos())){
                break;
            }
        }

        if( i == roads.length ){
            return null;
        } else{
            return roads[i];
        }
    }

    public float eta(Vector2 velocity){
        float length = totalRoadLength();
        float speed = velocity.len();
        return length * speed;
    }

    private float totalRoadLength(){
        float sum = 0;
        for(Road r : roads){
            sum += r.getLength();
        }
        return sum;
    }
}
