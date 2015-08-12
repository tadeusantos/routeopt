package org.tadeusantos.routeopt.services.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tadeusantos.routeopt.domain.Point;
import org.tadeusantos.routeopt.domain.Subroute;
import org.tadeusantos.routeopt.repositories.SubrouteRepository;
import org.tadeusantos.routeopt.services.contract.IPointManagementServices;
import org.tadeusantos.routeopt.services.contract.ISubrouteManagementServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedSubrouteException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;
import org.tadeusantos.routeopt.services.exceptions.SubrouteNotFoundException;

/**
 * Subroute creating and maintenance.
 * 
 * 
 * @author Tadeu Santos
 *
 */
@Service
public class SubrouteManagementServices implements ISubrouteManagementServices {

	@Autowired
	private SubrouteRepository repository;

	@Autowired
	private IPointManagementServices pointService;

	public SubrouteManagementServices() {
	}

	@Override
	public Subroute create(String pointFromName, String pointToName, double distance)
			throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		validateCreateRequest(pointFromName, pointToName);

		Point from = pointService.get(pointFromName);
		Point to = pointService.get(pointToName);

		validatePoints(from, to);
		validateExistingSubroute(from, to);
		return repository.save(new Subroute(from, to, distance));
	}

	private void validateExistingSubroute(Point from, Point to) throws DuplicatedSubrouteException {
		if (!repository.findByFromAndTo(from, to).isEmpty() ||
			!repository.findByFromAndTo(to, from).isEmpty()) {
			throw new DuplicatedSubrouteException("exceptions.subroute.duplicated");
		}
		
	}

	@Override
	public List<Subroute> listAll() {
		return repository.findAll();
	}

	@Override
	public void delete(String id) throws AmbiguousCritereaException, SubrouteNotFoundException {
		Subroute subroute = repository.findOne(id);

		if (subroute == null) {
			throw new SubrouteNotFoundException();
		}

		repository.delete(subroute);
	}

	private void validatePoints(Point from, Point to) throws InvalidPointNameException {
		if (from == null) {
			throw new InvalidPointNameException("exceptions.point.notfound.from");
		}
		if (to == null) {
			throw new InvalidPointNameException("exceptions.point.notfound.to");
		}
	}

	private void validateCreateRequest(String pointFromName, String pointToName)
			throws InvalidPointNameException, AmbiguousCritereaException {
		if (StringUtils.isEmpty(pointFromName) || StringUtils.isEmpty(pointToName)) {
			throw new InvalidPointNameException("exceptions.point.required.fromAndTo");
		}
	}
}
