package com.unimelb.swen30006.partc.planning;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.tong.Navigation;

import java.util.ArrayList;

/**
 * Created by Sean on 10/1/2016.
 */
public class SimplePriorityStrategy implements PriorityStrategy {
    public static float SAFETY_DISTANCE = 30f;
    public static float TRAFFIC_REACTION_DISTANCE = 50f;

    private Car car;
    private PerceptionResponse[] perceptionResponses;

    public SimplePriorityStrategy(Car car) {
        this.car = car;
    }

    public PerceptionResponse getHighesPriority(PerceptionResponse[] perceptionResponses, CarState state){
        this.perceptionResponses = perceptionResponses;

        CarState.State st = state.getState();
        if(st == CarState.State.STRAIGHT){
            return goStraight();
        } else if(st == CarState.State.LEFT){
            return goStraight();
        } else{
            return goStraight();
        }
    }

    private PerceptionResponse goStraight(){
        PerceptionResponse obstacleAhead = getObstacleAhead();
        PerceptionResponse trafficLight  = trafficLight();

        if(obstacleAhead != null && obstacleAhead.objectType != PerceptionResponse.Classification.TrafficLight){
            return obstacleAhead;
        } else {
            return trafficLight;
        }
    }

    private PerceptionResponse getObstacleAhead(){
        ArrayList<PerceptionResponse> prs = new ArrayList<PerceptionResponse>();
        Vector2 carDirection = this.car.getDirection();
        Vector2 obstacleDirection;
        for(PerceptionResponse pr : this.perceptionResponses){
            obstacleDirection = new Vector2((float)(pr.position.x-this.car.getPosition().x),
                    (float)(pr.position.y-this.car.getPosition().y));
            float dis = obstacleDirection.dot(carDirection);
            if((dis > 0) && (Math.sqrt(obstacleDirection.len2() - dis * dis)-pr.width/2) < this.car.getWidth()){
                prs.add(pr);
            }
        }

        PerceptionResponse closest = null;
        double dist = SAFETY_DISTANCE;
        for(PerceptionResponse pr : prs){
            double prDist = pr.position.distance(this.car.getPosition());
            if(prDist < dist){
                dist = prDist;
                closest = pr;
            }
        }

        return closest;
    }

    private PerceptionResponse trafficLight(){
        ArrayList<PerceptionResponse> trafficLights = new ArrayList<PerceptionResponse>(4);
        Vector2 carDir = car.getDirection();
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
                trafficLight.position.distance(car.getPosition()) > TRAFFIC_REACTION_DISTANCE){
            trafficLight = null;
        }

        if(trafficLight != null && trafficLight.information.get("state") == Color.GREEN) {
            trafficLight = null;
        }
        return trafficLight;
    }

    private PerceptionResponse findMaxDist(ArrayList<PerceptionResponse> perceptionResponses){
        PerceptionResponse perceptionResponse = null;
        double maxDist = 0;
        for(PerceptionResponse pr : perceptionResponses){
            double dist = car.getPosition().distance(pr.position);
            if(dist > maxDist){
                perceptionResponse = pr;
                maxDist = dist;
            }
        }
        return perceptionResponse;
    }
}
