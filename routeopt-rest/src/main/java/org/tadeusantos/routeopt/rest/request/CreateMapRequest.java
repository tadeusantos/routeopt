package org.tadeusantos.routeopt.rest.request;

public class CreateMapRequest {
	private String name;
	private String routes;

	public CreateMapRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoutes() {
		return routes;
	}

	public void setRoutes(String routes) {
		this.routes = routes;
	}

}
