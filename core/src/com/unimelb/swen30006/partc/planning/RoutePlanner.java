package com.unimelb.swen30006.partc.planning;

import java.awt.geom.Point2D;

/**
 * The interface plan the route for the car
 */
public interface RoutePlanner {
    /**
     * This method plan the route for the car based on the departure position and destination
     * @param depart departure position
     * @param destination destination postion
     * @return the route that the car should passes through
     */
    public Route getRoute(Point2D.Double depart, Point2D.Double destination);
}
