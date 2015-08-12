package org.tadeusantos.routeopt.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tadeusantos.routeopt.rest.response.Response;
import org.tadeusantos.routeopt.rest.response.ResponseStatus;
import org.tadeusantos.routeopt.services.contract.IRouteOptimizationServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;

/**
 * Route Optimization REST API
 * 
 * Operations available: GET
 * 
 * @author Tadeu Santos
 *
 */
@RequestMapping(value="/optimize")
@RestController
public class RouteOptimizationController {
	private static final Logger logger = 
			LoggerFactory.getLogger(RouteOptimizationController.class);

	@Autowired
	private IRouteOptimizationServices services;
	
	public RouteOptimizationController() {
	}
	
	@RequestMapping(value="/{from}/{to}/{autonomy:.*}/{price:.*}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response optimize(@PathVariable("from") String from, @PathVariable("to") String to, 
								   @PathVariable("autonomy") double truckAutonomy, 
								   @PathVariable("price") double gasPrice) {
		
		try {
			return new Response(ResponseStatus.OK, 
								null, 
								services.optimize(from, to, truckAutonomy, gasPrice)
								);
		} 
		catch (AmbiguousCritereaException e) {
			logger.info(e.getMessage(), e);
			
			return new Response(ResponseStatus.NOK, e.getLocalizedMessage(), null);
		}
	}

}
