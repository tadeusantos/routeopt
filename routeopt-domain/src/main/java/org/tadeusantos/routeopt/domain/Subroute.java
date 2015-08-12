package org.tadeusantos.routeopt.domain;

import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Subroute represents a possible leg between two locations.
 * 
 * This class is mapped to a document collection in MongoDB.
 * 
 * @author Tadeu Santos
 *
 */
@Document
public class Subroute {
	@Id
	private String id;
	private String name;
	private String map;
	private String from;
	private String to;
	private double distance;

	public Subroute() {

	}

	public Subroute(String map, String from, String to, double distance) {
		if (StringUtils.isEmpty(map)) {
			throw new IllegalArgumentException("exceptions.subroute.required.map");
		}
		if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to)) {
			throw new IllegalArgumentException("exceptions.subroute.required.points");
		}
		if (distance < 0) {
			throw new IllegalArgumentException("exceptions.subroute.positive.distance");
		}

		this.map = map;
		this.from = from;
		this.to = to;
		this.distance = distance;

		this.name = String.format("%s -> %s", from, to);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}
}
