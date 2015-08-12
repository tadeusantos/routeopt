package org.tadeusantos.routeopt.rest.controllers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tadeusantos.routeopt.rest.request.CreateMapRequest;
import org.tadeusantos.routeopt.rest.response.Response;
import org.tadeusantos.routeopt.rest.response.ResponseStatus;
import org.tadeusantos.routeopt.services.contract.ISubrouteManagementServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedSubrouteException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;
import org.tadeusantos.routeopt.services.exceptions.SubrouteNotFoundException;

/**
 * Subroute REST API
 * 
 * Operations available: GET, POST, DELETE
 * 
 * @author Tadeu Santos
 *
 */
@RequestMapping(value="/map")
@RestController
public class MapController {
	private static final Logger logger = 
			LoggerFactory.getLogger(MapController.class);

	@Autowired
	private ISubrouteManagementServices services;
	
	public MapController() {
	}
	
	@RequestMapping(value="", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response listAll() {
		return new Response(ResponseStatus.OK, null, services.listAll(null));
	}
	
	@RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response create(@RequestBody CreateMapRequest request)  {
		validateCreateMapRequest(request);
		String[] routes = request.getRoutes().split("\n");
		int validRoutes = routes.length;
		String messages = "";
		for (String iRoute : routes) {
			if (StringUtils.isEmpty(iRoute)) {
				--validRoutes;
				continue;
			}
			
			String[] route = iRoute.split(" ");
			
			if (isValidRoute(route)) {
				try {
					services.create(request.getName(), route[0], 
									route[1], Double.parseDouble(route[2]));
		
				}
				catch (AmbiguousCritereaException | DuplicatedSubrouteException | InvalidPointNameException e) {
					logger.info(e.getMessage(), e);
					
					--validRoutes;
					messages += String.format("Skiping %s: %s\n", iRoute, e.getMessage());
				}
			}
			else {
				--validRoutes;
				messages += String.format("Skiping %s: Invalid format\n", iRoute);
			}
		}
		
		ResponseStatus status = ResponseStatus.OK;
		if (validRoutes == 0) {
			status = ResponseStatus.NOK;
		}
		
		return new Response(status, messages, null);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response listMap(String map) {
		return new Response(ResponseStatus.OK, null, services.listAll(map));
	}
	

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response delete(@PathVariable("id") String id) {
		try {
			services.delete(id);
			
			return new Response(ResponseStatus.OK, null, null);
		} 
		catch (IllegalArgumentException | AmbiguousCritereaException | SubrouteNotFoundException e) {
			logger.info(e.getMessage(), e);
			
			return new Response(ResponseStatus.NOK, e.getLocalizedMessage(), null);
		}
		
	}
	
	private void validateCreateMapRequest(CreateMapRequest request) {
		if (StringUtils.isEmpty(request.getName())) {
			throw new IllegalArgumentException("exceptions.map.required.name");
		}
		if (StringUtils.isEmpty(request.getRoutes())) {
			throw new IllegalArgumentException("exceptions.map.required.routes");
		}
	}

	private boolean isValidRoute(String[] route) {
		boolean isValid = true;
		
		isValid = route.length == 3;
		isValid = StringUtils.isNoneEmpty(route[0]) && StringUtils.isNoneEmpty(route[1]) &&
				StringUtils.isNoneEmpty(route[2]);
		
		try {
			Double.parseDouble(route[2]);
		}
		catch (NumberFormatException e) {
			isValid = false;
		}
		
		return isValid;
	}

}
