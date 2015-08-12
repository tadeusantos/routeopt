package org.tadeusantos.routeopt.services.contract;

import org.tadeusantos.routeopt.services.dto.OptimizedRoute;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;


/**
 * Service contract for Route Optimization.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public interface IRouteOptimizationServices {
	OptimizedRoute optimize(String fromName, String toName, double truckAutonomy, double gasPrice) throws AmbiguousCritereaException;
}
