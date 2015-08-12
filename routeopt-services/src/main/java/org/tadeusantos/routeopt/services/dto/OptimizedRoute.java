package org.tadeusantos.routeopt.services.dto;

import org.tadeusantos.routeopt.domain.Point;


/**
 * Optimized route information that will be returned to the rest module.
 * 
 * The estimated route message contains among others: The locations route, 
 * the estimated distance and the estimated trip cost.
 * 
 * @author Tadeu Santos
 *
 */
public class OptimizedRoute {
	private Point from;
	private Point to;
	private String route;
	private double estimatedCost;
	private double estimatedDistance;
	
	public OptimizedRoute() {
		
	}
	
	public Point getFrom() {
		return from;
	}
	public void setFrom(Point from) {
		this.from = from;
	}
	public Point getTo() {
		return to;
	}
	public void setTo(Point to) {
		this.to = to;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public double getEstimatedCost() {
		return estimatedCost;
	}
	public void setEstimatedCost(double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
	public double getEstimatedDistance() {
		return estimatedDistance;
	}
	public void setEstimatedDistance(double estimatedDistance) {
		this.estimatedDistance = estimatedDistance;
	}
}
