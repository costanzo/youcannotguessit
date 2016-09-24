package com.unimelb.swen30006.partc.perception;

import java.awt.geom.Point2D;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.unimelb.swen30006.partc.ai.interfaces.IPerception;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse;
import com.unimelb.swen30006.partc.ai.interfaces.PerceptionResponse.Classification;
import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.infrastructure.Light;
import com.unimelb.swen30006.partc.core.infrastructure.Sign;
import com.unimelb.swen30006.partc.core.infrastructure.TrafficLight;
import com.unimelb.swen30006.partc.core.objects.Building;
import com.unimelb.swen30006.partc.core.objects.Car;
import com.unimelb.swen30006.partc.core.objects.WorldObject;

public class PerceptionForTesting implements IPerception {
	private World world;
	

	public PerceptionForTesting(World world) {
		this.world = world;
	}


	@Override
	public PerceptionResponse[] analyseSurroundings(Point2D.Double currentPos) {
		WorldObject[] objects = world.objectsAtPoint(currentPos);
		PerceptionResponse[] responses = new PerceptionResponse[objects.length];
		for (int i = 0; i < objects.length; i++){
			responses[i] = convertWorldObjectToResponse(currentPos, objects[i]);
		}
		
		return responses;
	}

	private PerceptionResponse convertWorldObjectToResponse(Point2D.Double currentPos, WorldObject worldObject){
		Point2D.Double position;
		float width;
		float length;
		Vector2 direction = new Vector2();
		Classification objectType;
		HashMap<String, Object> information = null;
		
		position = worldObject.getPosition();
		width = worldObject.getWidth();
		length = worldObject.getLength();
		direction.x = Float.parseFloat((position.getX() - currentPos.getX())+"");
		direction.y = Float.parseFloat((position.getY() - currentPos.getY())+"");
		objectType = getType(worldObject);
		if (objectType == Classification.TrafficLight){
			information = new HashMap<String, Object>();
			information.put("state", ((TrafficLight)worldObject).getColour());
		}
		
		PerceptionResponse response = new PerceptionResponse(position, width, length, direction, objectType, information);
		return response;
	}
	
	private Classification getType(WorldObject worldObject){
		if (worldObject instanceof  Building){
			return Classification.Building;
		} else if (worldObject instanceof TrafficLight){
			return Classification.TrafficLight;
		} else if (worldObject instanceof Car){
			return Classification.Car;
		} else if (worldObject instanceof Sign){
			return Classification.Sign;
		} else if (worldObject instanceof Light){
			return Classification.StreetLight;
		} else {
			return Classification.Unknown;
		}
	}
}
