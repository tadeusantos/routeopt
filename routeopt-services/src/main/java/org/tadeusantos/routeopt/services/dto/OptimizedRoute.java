package org.tadeusantos.routeopt.services.dto;

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
	private String from;
	private String to;
	private String route;
	private double estimatedCost;
	private double estimatedDistance;
	
	public OptimizedRoute() {
		
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
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
