package org.tadeusantos.routeopt.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tadeusantos.routeopt.repositories.PointRepository;
import org.tadeusantos.routeopt.repositories.SubrouteRepository;
import org.tadeusantos.routeopt.services.contract.IUnitTestsHelperServices;

/**
 * Utility service used to reset the database for unit tests.
 * 
 * 
 * @author Tadeu Santos
 *
 */
@Service
public class UnitTestsHelperServices implements IUnitTestsHelperServices {
	@Autowired
	private PointRepository pointRepository;
	@Autowired
	private SubrouteRepository subrouteRepository;
	
	@Override
	public void deleteAllPoints() {
		pointRepository.deleteAll();
	}

	@Override
	public void deleteAllSubroutes() {
		subrouteRepository.deleteAll();
	}

}
