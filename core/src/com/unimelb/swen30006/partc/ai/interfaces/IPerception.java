package com.unimelb.swen30006.partc.ai.interfaces;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * The interface for the perception sub system
 * @author Mathew Blair <mathew.blair@unimelb.edu.au>
 * @author Yunzhe(Alvin) Jia <yunzhe.jia@unimelb.edu.au>
 * @version 2.0
 */
public interface IPerception {
	
	/**
	 * Analyses the perception response and determines the appropriate action to take
	 * @param currentPos the position of car
	 * @return
	 */
	public PerceptionResponse[] analyseSurroundings(Point2D.Double currentPos);
	
}
