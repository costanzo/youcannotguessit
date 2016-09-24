package com.unimelb.swen30006.partc.controllers;

import com.unimelb.swen30006.partc.ai.interfaces.*;
import com.unimelb.swen30006.partc.core.World;
import com.unimelb.swen30006.partc.core.objects.Car;


/**
 * The AI controller for use in integrating your systems with the simulation.
 * DO NOT MODIFY THIS FILE!!
 * @author Mathew Blair <mathew.blair@unimelb.edu.au>
 * @version 1.0
 */
public class AIController extends Controller {
	
	// The interfaces used to update the world
	private IPlanning planner;
	private IPerception perception;
	
	public AIController(Car car,IPlanning planner, IPerception perception) {
		super(car);
		this.perception = perception;
		this.planner = planner;
	}

	@Override
	public void update(float delta) {
		// first updating perception
		PerceptionResponse[] responses = perception.analyseSurroundings(car.getPosition());
		// Finally update planner
		planner.update(responses, World.VISIBILITY_RADIUS, delta);	
	}

}
