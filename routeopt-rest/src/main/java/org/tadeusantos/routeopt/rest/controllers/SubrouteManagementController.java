package org.tadeusantos.routeopt.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping(value="/subroutes")
@RestController
public class SubrouteManagementController {
	private static final Logger logger = 
			LoggerFactory.getLogger(SubrouteManagementController.class);

	@Autowired
	private ISubrouteManagementServices services;
	
	public SubrouteManagementController() {
	}
	
	@RequestMapping(value="", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response listAll() {
		return new Response(ResponseStatus.OK, null, services.listAll());
	}
	
	@RequestMapping(value="/{from}/{to}/{distance:.*}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response create(@PathVariable("from") String from, @PathVariable("to") String to,
							@PathVariable("distance") double distance)  {
		try {
			return new Response(ResponseStatus.OK, null, services.create(from, to, distance));
		} 
		catch (AmbiguousCritereaException | DuplicatedSubrouteException | InvalidPointNameException e) {
			logger.info(e.getMessage(), e);
			
			return new Response(ResponseStatus.NOK, e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response delete(@PathVariable("id") String id) {
		try {
			services.delete(id);
			
			return new Response(ResponseStatus.OK, null, null);
		} 
		catch (AmbiguousCritereaException | SubrouteNotFoundException e) {
			logger.info(e.getMessage(), e);
			
			return new Response(ResponseStatus.NOK, e.getLocalizedMessage(), null);
		}
		
	}
}
