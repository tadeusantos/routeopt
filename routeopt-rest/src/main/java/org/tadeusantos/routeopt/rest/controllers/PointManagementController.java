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
import org.tadeusantos.routeopt.services.contract.IPointManagementServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedPointException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;
import org.tadeusantos.routeopt.services.exceptions.PointNotFoundException;

/**
 * Point REST API
 * 
 * Operations available: GET, POST, DELETE
 * 
 * @author Tadeu Santos
 *
 */
@RequestMapping(value="/points")
@RestController
public class PointManagementController {
	private static final Logger logger = 
			LoggerFactory.getLogger(PointManagementController.class);

	@Autowired
	private IPointManagementServices services;
	
	public PointManagementController() {
	}
	
	@RequestMapping(value="", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response listAll() {
		return new Response(ResponseStatus.OK, null, services.listAll());
	}
	
	@RequestMapping(value="/{name}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response get(@PathVariable("name") String name) {
		try {
			return new Response(ResponseStatus.OK, null, services.get(name));
		} 
		catch (AmbiguousCritereaException e) {
			logger.info(e.getMessage(), e);
			
			return new Response(ResponseStatus.NOK, e.getLocalizedMessage(), null);
		}
	}

	@RequestMapping(value="/{name}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response create(@PathVariable("name") String name)  {
		try {
			return new Response(ResponseStatus.OK, null, services.create(name));
		} 
		catch (DuplicatedPointException | InvalidPointNameException e) {
			logger.info(e.getMessage(), e);
			
			return new Response(ResponseStatus.NOK, e.getLocalizedMessage(), null);
		}
	}
	
	@RequestMapping(value="/{name}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response delete(@PathVariable("name") String name) {
		try {
			services.delete(name);
			
			return new Response(ResponseStatus.OK, null, null);
		} 
		catch (AmbiguousCritereaException | PointNotFoundException e) {
			logger.info(e.getMessage(), e);
			
			return new Response(ResponseStatus.NOK, e.getLocalizedMessage(), null);
		}
	}
}
