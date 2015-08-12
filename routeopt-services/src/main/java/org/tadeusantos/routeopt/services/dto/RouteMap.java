package org.tadeusantos.routeopt.services.dto;

import java.util.ArrayList;
import java.util.List;

import org.tadeusantos.routeopt.services.impl.RouteOptimizationServices;

/**
 * Service map is the graph created from the data model.
 * This graph will be used on the optimization algorithm.
 * 
 * @see RouteOptimizationServices
 * @author Tadeu Santos
 *
 */
public class RouteMap {

	private List<RouteLeg> legs;
	private List<RoutePoint> points;
	
	public RouteMap() {
		this.legs = new ArrayList<>();
		this.points = new ArrayList<>();
	}

	public List<RouteLeg> getLegs() {
		return legs;
	}

	public void setLegs(List<RouteLeg> legs) {
		this.legs = legs;
	}

	public List<RoutePoint> getPoints() {
		return points;
	}

	public void setPoints(List<RoutePoint> points) {
		this.points = points;
	}

}
