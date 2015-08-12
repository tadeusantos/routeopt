package org.tadeusantos.routeopt.services.dto;


/**
 * Route leg is an edge in the graph that represents the WM Company location map.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public class RouteLeg {

	private String id;
	private RoutePoint from;
	private RoutePoint to;
	private double distance;

	public RouteLeg() {
	}

	public RouteLeg(String id, RoutePoint from, RoutePoint to, double distance) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	public String getId() {
		return id;
	}

	public RoutePoint getFrom() {
		return from;
	}

	public RoutePoint getTo() {
		return to;
	}

	public double getDistance() {
		return distance;
	}

}
