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
    public static float SAFETY_DISTANCE = 30f;
    public static float TRAFFIC_REACTION_DISTANCE = 50f;
    public static float SAFETY_WIDTH = 8f;

    private PerceptionResponse[] perceptionResponses;

    public SimplePriorityStrategy() {
        this.perceptionResponses = null;
    }

    public PerceptionResponse getHighesPriority(PerceptionResponse[] perceptionResponses, CarState state){
        this.perceptionResponses = perceptionResponses;

        CarState.State st = state.getState();
        if(st == CarState.State.STRAIGHT){
            return goStraight(state);
        } else if(st == CarState.State.LEFT){
            return goStraight(state);
        } else{
            return goStraight(state);
        }
    }

    private PerceptionResponse goStraight(CarState state){
        PerceptionResponse obstacleAhead = getObstacleAhead(state.getDirection());
        PerceptionResponse trafficLight  = trafficLight(state.getDirection());

        if(obstacleAhead != null && obstacleAhead.objectType != PerceptionResponse.Classification.TrafficLight){
            return obstacleAhead;
        } else {
            return trafficLight;
        }
    }

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

    private PerceptionResponse trafficLight(Vector2 carDir){
        ArrayList<PerceptionResponse> trafficLights = new ArrayList<PerceptionResponse>(4);
        for(PerceptionResponse pr : perceptionResponses){
            if(pr.objectType == PerceptionResponse.Classification.TrafficLight){
                if(carDir.dot(pr.direction) > 0){
                    trafficLights.add(pr);
                }
            }
        }

        if( trafficLights.size() < 4 ) {
            return null;
        }

        PerceptionResponse trafficLight = null;
        PerceptionResponse pr = findMaxDist(trafficLights);
        trafficLights.remove(pr);
        pr = findMaxDist(trafficLights);
        trafficLight = pr;

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
