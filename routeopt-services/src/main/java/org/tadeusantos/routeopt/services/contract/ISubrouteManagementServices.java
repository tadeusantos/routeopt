package org.tadeusantos.routeopt.services.contract;

import java.util.List;

import org.tadeusantos.routeopt.domain.Subroute;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedSubrouteException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;
import org.tadeusantos.routeopt.services.exceptions.SubrouteNotFoundException;

/**
 * Service contract for Subroute Management.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public interface ISubrouteManagementServices {
	Subroute create(String pointFromName, String pointToName, double distance) throws AmbiguousCritereaException, 
		DuplicatedSubrouteException, InvalidPointNameException;
	List<Subroute> listAll();
	void delete(String id) throws AmbiguousCritereaException, SubrouteNotFoundException;
}
