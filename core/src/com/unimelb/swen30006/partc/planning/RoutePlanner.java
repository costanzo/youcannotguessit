package com.unimelb.swen30006.partc.planning;

import java.awt.geom.Point2D;

/**
 * Created by Sean on 9/30/2016.
 */
public interface RoutePlanner {
    public Route getRoute(Point2D.Double depart, Point2D.Double destination);
}
