package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.tong.Navigation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Sean on 10/1/2016.
 */
public class SimplePriorityStrategy implements PriorityStrategy {
    public static final float SAFETY_DISTANCE = 20f;
    public static final float TRAFFIC_REACTION_DISTANCE = 50f;
    public static final float SAFETY_WIDTH = 6f;

    private PerceptionResponse[] perceptionResponses;

    public SimplePriorityStrategy() {
        this.perceptionResponses = null;
    }

    public PerceptionResponse getHighestPriority(PerceptionResponse[] perceptionResponses, CarState state){
        this.perceptionResponses = perceptionResponses;

        Vector2 carDir = state.getDirection();
        Point2D.Double carPos = state.getPos();

        PerceptionResponse obstacleAhead = getObstacleAhead(carDir);
        PerceptionResponse trafficLight  = trafficLight(carDir, carPos);

        if(obstacleAhead != null && obstacleAhead.objectType != PerceptionResponse.Classification.TrafficLight){
            return obstacleAhead;
        } else {
            return trafficLight;
        }
    }

    //find the closest obstacles ahead that the car might hit including the traffic lights
    private PerceptionResponse getObstacleAhead(Vector2 carDirection){
        ArrayList<PerceptionResponse> prs = new ArrayList<PerceptionResponse>();
        for(PerceptionResponse pr : this.perceptionResponses){
            float dis = pr.direction.dot(carDirection);
            if((dis > 0) && (Math.sqrt(pr.direction.len2() - dis * dis)-pr.width/2) < SAFETY_WIDTH){
                prs.add(pr);
            }
        }

        PerceptionResponse closest = null;
        double dist = SAFETY_DISTANCE;
        for(PerceptionResponse pr : prs){
            double prDist = pr.direction.len();
            if(prDist < dist){
                dist = prDist;
                closest = pr;
            }
        }

        return closest;
    }

    private PerceptionResponse trafficLight(Vector2 carDir, Point2D.Double carPos){
        ArrayList<PerceptionResponse> trafficLights = new ArrayList<PerceptionResponse>();
        //find the traffic lights ahead
        for(PerceptionResponse pr : perceptionResponses){
            if(pr.objectType == PerceptionResponse.Classification.TrafficLight){
                //the traffic lights must be in front of the car
                if(carDir.dot(pr.direction) > 0 && carPos.distance(pr.position) < TRAFFIC_REACTION_DISTANCE){
                    trafficLights.add(pr);
                }
            }
        }

        //the car must can see the four lights
        if( trafficLights.size() < 4 ) {
            return null;
        }

        PerceptionResponse trafficLight = findMaxDist(trafficLights);
        trafficLights.remove(trafficLight);
        trafficLight = findMaxDist(trafficLights);

        //if the traffic light is too far, we do not consider it
        if(trafficLight != null &&
                trafficLight.direction.len() > TRAFFIC_REACTION_DISTANCE){
            trafficLight = null;
        }
        return trafficLight;
    }

    private PerceptionResponse findMaxDist(ArrayList<PerceptionResponse> perceptionResponses){
        PerceptionResponse perceptionResponse = null;
        double maxDist = 0;
        for(PerceptionResponse pr : perceptionResponses){
            double dist = pr.direction.len();
            if(dist > maxDist){
                perceptionResponse = pr;
                maxDist = dist;
            }
        }
        return perceptionResponse;
    }
}
