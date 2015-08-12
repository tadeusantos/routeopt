package org.tadeusantos.routeopt.services.contract;


/**
 * Utility class used to reset the database during unit tests.
 * 
 * 
 * @author Tadeu Santos
 *
 */
public interface IUnitTestsHelperServices {
	void deleteAllSubroutes();
}
