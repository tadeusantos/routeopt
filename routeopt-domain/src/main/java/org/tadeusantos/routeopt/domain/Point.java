package org.tadeusantos.routeopt.domain;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Point represents an important location in the WM Company supply chain.
 * 
 * This class is mapped to a document collection in MongoDB.
 * 
 * @author Tadeu Santos
 *
 */

@Document
public class Point {
	@Id
	private String id;
	protected String name;

	public Point() {

	}

	public Point(String name) {
		this.name = name;
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

}
