package org.tadeusantos.routeopt.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tadeusantos.routeopt.domain.Subroute;
import org.tadeusantos.routeopt.repositories.SubrouteRepository;
import org.tadeusantos.routeopt.services.contract.IRouteOptimizationServices;
import org.tadeusantos.routeopt.services.dto.OptimizedRoute;
import org.tadeusantos.routeopt.services.dto.RouteLeg;
import org.tadeusantos.routeopt.services.dto.RouteMap;
import org.tadeusantos.routeopt.services.dto.RoutePoint;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;

/**
 * This service is responsible for optimizing the route between two locations (points).
 * It implements an algorithm based on Dijkstra's algorithm for finding the shortest path 
 * between two nodes in a graph.
 * 
 * {@link https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm}
 * 
 * @see RouteMap
 * @see RoutePoint
 * @see RouteLeg
 * 
 * @author Tadeu Santos
 *
 */
@Service
public class RouteOptimizationServices implements IRouteOptimizationServices {

	private RouteMap map = null;

	private Set<RoutePoint> settledPoints = new HashSet<>();
	private Set<RoutePoint> unsettledPoints = new HashSet<>();
	private Map<RoutePoint, Double> distancies = new HashMap<>();
	private Map<RoutePoint, RoutePoint> predecessors = new HashMap<>();

	@Autowired
	private SubrouteRepository repository;

	public RouteOptimizationServices() {
	}

	@Override
	public OptimizedRoute optimize(String mapName, String from, String to, double truckAutonomy, double gasPrice) throws AmbiguousCritereaException {
		
		RoutePoint fromPoint = new RoutePoint(from);
		RoutePoint toPoint = new RoutePoint(to);
		map = loadRouteMap(mapName);

		distancies.put(fromPoint, 0d);

		unsettledPoints.add(fromPoint);

		while (unsettledPoints.size() > 0) {
			RoutePoint point = getMinimum(unsettledPoints);
			settledPoints.add(point);
			unsettledPoints.remove(point);

			refresh(point);
		}

		OptimizedRoute route = loadOptimizedRoute(truckAutonomy, gasPrice, from, to, toPoint);
		return route;
	}

	private OptimizedRoute loadOptimizedRoute(double truckAutonomy, double gasPrice, String from, String to,
			RoutePoint toPoint) {
		LinkedList<RoutePoint> optimizedPath = getPath(toPoint);
		
		StringBuilder stringBuilder = new StringBuilder();
		for (RoutePoint routePoint : optimizedPath) {
			stringBuilder.append(routePoint.getPointName()).append(" ");
		}
		
		OptimizedRoute route = new OptimizedRoute();
		double estimatedDistance = distancies.get(toPoint);
		route.setFrom(from);
		route.setTo(to);
		route.setEstimatedDistance(estimatedDistance);
		route.setEstimatedCost(estimatedDistance * gasPrice / truckAutonomy);


		route.setRoute(StringUtils.trim(stringBuilder.toString()));
		return route;
	}

	private RouteMap loadRouteMap(String mapName) {
		RouteMap map = new RouteMap();
		
		if (StringUtils.isEmpty(mapName)) {
			throw new IllegalArgumentException("exception.optimize.required.map");
		}
		
		List<Subroute> subroutes = repository.findByMap(mapName);
		for (Subroute subroute : subroutes) {
			RoutePoint routeFrom = new RoutePoint(subroute.getFrom());

			if (!map.getPoints().contains(routeFrom)) {
				map.getPoints().add(routeFrom);
			}

			map.getLegs().add(createLeg(subroute));
		}
		
		subroutes.clear();
		return map;
	}

	private RouteLeg createLeg(Subroute subroute) {
		return new RouteLeg(subroute.getName(), new RoutePoint(subroute.getFrom()),
				new RoutePoint(subroute.getTo()), subroute.getDistance());
	}

	private RoutePoint getMinimum(Set<RoutePoint> points) {
		RoutePoint minimum = null;
		for (RoutePoint point : points) {
			if (minimum == null) {
				minimum = point;
			} else {
				if (getShortestDistance(point) < getShortestDistance(minimum)) {
					minimum = point;
				}
			}
		}
		return minimum;
	}

	private double getShortestDistance(RoutePoint destination) {
		Double distance = distancies.get(destination);

		if (distance == null) {
			return Double.MAX_VALUE;
		} else {
			return distance;
		}
	}

	private void refresh(RoutePoint node) {
		List<RoutePoint> adjacentNodes = getNeighbors(node);

		for (RoutePoint target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distancies.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unsettledPoints.add(target);
			}
		}

	}

	private List<RoutePoint> getNeighbors(RoutePoint point) {
		List<RoutePoint> neighbors = new ArrayList<RoutePoint>();

		for (RouteLeg edge : map.getLegs()) {
			if (edge.getFrom().equals(point) && !isSettled(edge.getTo())) {
				neighbors.add(edge.getTo());
			}
		}

		return neighbors;
	}

	private boolean isSettled(RoutePoint point) {
		return settledPoints.contains(point);
	}

	private double getDistance(RoutePoint from, RoutePoint target) {
		double distance = Double.MAX_VALUE;

		for (RouteLeg to : map.getLegs()) {
			if (to.getFrom().equals(from) && to.getTo().equals(target)) {
				distance = to.getDistance();
			}
		}

		return distance;
	}

	private LinkedList<RoutePoint> getPath(RoutePoint toPoint) {
		LinkedList<RoutePoint> path = new LinkedList<RoutePoint>();
		RoutePoint step = toPoint;

		if (predecessors.get(step) == null) {
			return null;
		}
		
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		
		Collections.reverse(path);
		
		return path;
	}

}
