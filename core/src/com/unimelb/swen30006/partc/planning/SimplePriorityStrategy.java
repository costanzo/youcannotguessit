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

    private Car car;

    public SimplePriorityStrategy(Car car) {
        this.car = car;
    }

    public PerceptionResponse getHighesPriority(PerceptionResponse[] perceptionResponses, Navigation.CarState state){
        if(state == Navigation.CarState.STRAIGHT){
            Color trafficLightColor = trafficLight(perceptionResponses);
            if(trafficLightColor == null){
                System.out.println("Cannot see traffic light");
            } else {
                String c = null;
                if(trafficLightColor == Color.RED)
                    c = "Red";
                else if(trafficLightColor == Color.GREEN)
                    c = "Green";
                else
                    c = "Amber";
                System.out.println(c);
            }
        } else if(state == Navigation.CarState.LEFT){

        } else{

        }
        return null;
    }

    private PerceptionResponse goStraight(PerceptionResponse[] perceptionResponses){
        return null;
    }

    private Color trafficLight(PerceptionResponse[] perceptionResponses){
        int sum = 0;
        ArrayList<PerceptionResponse> trafficLights = new ArrayList<PerceptionResponse>(4);
        for(PerceptionResponse pr : perceptionResponses){
            if(pr.objectType == PerceptionResponse.Classification.TrafficLight){
                sum++;
                trafficLights.add(pr);
            }
        }

        if( sum < 3 ) {
            return null;
        }

        Vector2 carDirection = car.getDirection();
        for(PerceptionResponse pr : trafficLights){
            Vector2 lightDirection = new Vector2((float)(pr.position.getX()-car.getPosition().getX()),
                    (float)(pr.position.getY()-car.getPosition().getY()));
            if(carDirection.dot(lightDirection) < 0){
                return null;
            }
        }

        if(sum == 3){
            PerceptionResponse pr = findMaxDist(trafficLights);
            return (Color)pr.information.get("state");
        } else if(sum == 4){
            PerceptionResponse pr = findMaxDist(trafficLights);
            trafficLights.remove(pr);
            pr = findMaxDist(trafficLights);
            return (Color)pr.information.get("state");
        }

        return null;
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
