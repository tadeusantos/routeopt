package org.tadeusantos.routeopt.services.contract;

import java.util.List;

import org.tadeusantos.routeopt.domain.Point;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedPointException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;
import org.tadeusantos.routeopt.services.exceptions.PointNotFoundException;

/**
 * Service contract for Point Management.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public interface IPointManagementServices {
	Point create(String name) throws DuplicatedPointException, InvalidPointNameException;
	List<Point> listAll();
	Point get(String name) throws AmbiguousCritereaException;
	void delete(String name) throws AmbiguousCritereaException, PointNotFoundException;
}
