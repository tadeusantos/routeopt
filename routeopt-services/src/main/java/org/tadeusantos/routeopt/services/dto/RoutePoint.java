package org.tadeusantos.routeopt.services.dto;


/**
 * Route point is a vertex in the graph that represents the WM Company location map.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public class RoutePoint {
	private String name;
	
	public RoutePoint(String pointName) {
		this.name = pointName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoutePoint other = (RoutePoint) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getPointName() {
		return name;
	}

	public void setPointName(String pointName) {
		this.name = pointName;
	}

}
