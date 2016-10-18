package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * This class will prioritise the perception responses based on the car state
 */
public class SimplePriorityStrategy implements PriorityStrategy {
    //the distance that the car should keep from other obstacles
    public static final float SAFETY_DISTANCE = 20f;

    //the reaction distance within which the car should take action about the traffic light
    public static final float TRAFFIC_REACTION_DISTANCE = 50f;

    //the distance from the track that the car will go through
    public static final float SAFETY_WIDTH = 4f;

    private PerceptionResponse[] perceptionResponses;

    public SimplePriorityStrategy() {
        this.perceptionResponses = null;
    }

    /**
     * This method is used to select the highest priority perception response
     * from the all the perception responses based on car state
     * @param perceptionResponses
     * @param state
     * @return the perception response with the highest priority
     */
    public PerceptionResponse getHighestPriority(PerceptionResponse[] perceptionResponses, CarState state){
        this.perceptionResponses = perceptionResponses;

        //the running direction of the car
        Vector2 carDir = state.getDirection();
        Point2D.Double carPos = state.getPos();

        //the obstacle in front of the car, including traffic lights
        PerceptionResponse obstacleAhead = getObstacleAhead(carDir);

        //the traffic light ahead that the car should watch out
        PerceptionResponse trafficLight  = trafficLight(carDir, carPos);

        if(obstacleAhead != null && obstacleAhead.objectType != PerceptionResponse.Classification.TrafficLight){
            //the obstacle in front of the car and the obstacle should not be traffic light
            return obstacleAhead;
        } else {
            //the traffic light to take care of
            return trafficLight;
        }
    }

    //find the closest obstacles ahead that the car might hit including the traffic lights
    private PerceptionResponse getObstacleAhead(Vector2 carDirection){
        ArrayList<PerceptionResponse> prs = new ArrayList<PerceptionResponse>();
        for(PerceptionResponse pr : this.perceptionResponses){
            float dis = pr.direction.dot(carDirection);
            if((dis > 0) && (Math.sqrt(pr.direction.len2() - dis * dis)-pr.width/2) < SAFETY_WIDTH){
                //the items in front that the car might collide with
                prs.add(pr);
            }
        }

        PerceptionResponse closest = null;
        double dist = SAFETY_DISTANCE;
        //find the closest item that the car might collide with
        for(PerceptionResponse pr : prs){
            double prDist = pr.direction.len();
            if(prDist < dist){
                dist = prDist;
                closest = pr;
            }
        }

        return closest;
    }

    //find the right traffic light that the car should take care of
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

        //get the right second traffic light
        PerceptionResponse trafficLight = findMaxDist(trafficLights);

        //remove the right second traffic light
        trafficLights.remove(trafficLight);

        //get the left second traffic light
        trafficLight = findMaxDist(trafficLights);

        //if the traffic light is too far, we do not consider it
        if(trafficLight != null &&
                trafficLight.direction.len() > TRAFFIC_REACTION_DISTANCE){
            trafficLight = null;
        }
        return trafficLight;
    }

    //find the furthest perception response from the perception response list
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
