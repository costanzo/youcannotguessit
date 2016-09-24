package com.unimelb.swen30006.partc.ai.interfaces;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

/**
 * The model for the perception responses passing between perception and planning.
 * @author Mathew Blair <mathew.blair@unimelb.edu.au>
 * @author Yunzhe(Alvin) Jia <yunzhe.jia@unimelb.edu.au>
 * @version 2.0
 */
public final class PerceptionResponse {

	// Enum for data
	public enum Classification {
		Building, TrafficLight, Car, Sign, StreetLight, Unknown
	}
	
	// Immutable data
	public final Point2D.Double position;
	public final float width;
	public final float length;
	public final Vector2 direction;
	public final Classification objectType;
	public final HashMap<String, Object> information;
	
	/**
	 * Creates a perception response object with the provided data, this will give an immutable class.
	 * @param position the center position of the object
	 * @param width the width of the object
	 * @param length the length of the object
	 * @param dir the direction from the current position to the object
	 * @param type the type of classification
	 * @param info any additional information that may be calculated by the perception subsystem.
	 */
	public PerceptionResponse(Double position, float width, float length, Vector2 direction, Classification objectType,
			HashMap<String, Object> information) {
		super();
		this.position = position;
		this.width = width;
		this.length = length;
		this.direction = direction;
		this.objectType = objectType;
		this.information = information;
	}

	@Override
	public String toString() {
		return "PerceptionResponse [position=" + position + ", width=" + width + ", length=" + length + ", direction="
				+ direction + ", objectType=" + objectType + ", information=" + information + "]";
	}
}
