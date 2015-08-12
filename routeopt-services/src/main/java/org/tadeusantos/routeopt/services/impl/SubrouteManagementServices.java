package org.tadeusantos.routeopt.services.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tadeusantos.routeopt.domain.Subroute;
import org.tadeusantos.routeopt.repositories.SubrouteRepository;
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

	public SubrouteManagementServices() {
	}

	@Override
	public Subroute create(String map, String from, String to, double distance)
			throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		validateCreateRequest(from, to);

		validateExistingSubroute(map, from, to);
		return repository.save(new Subroute(map, from, to, distance));
	}

	private void validateExistingSubroute(String map, String from, String to) throws DuplicatedSubrouteException {
		if (!repository.findByMapAndFromAndTo(map, from, to).isEmpty() ||
			!repository.findByMapAndFromAndTo(map, to, from).isEmpty()) {
			throw new DuplicatedSubrouteException("exceptions.subroute.duplicated");
		}
		
	}

	@Override
	public List<Subroute> listAll(String map) {
		if (StringUtils.isNotEmpty(map)) {
			return repository.findByMap(map);
		}
		else {
			return repository.findAll();
		}
	}

	@Override
	public void delete(String id) throws AmbiguousCritereaException, SubrouteNotFoundException {
		Subroute subroute = repository.findOne(id);

		if (subroute == null) {
			throw new SubrouteNotFoundException();
		}

		repository.delete(subroute);
	}

	private void validateCreateRequest(String pointFromName, String pointToName)
			throws InvalidPointNameException, AmbiguousCritereaException {
		if (StringUtils.isEmpty(pointFromName) || StringUtils.isEmpty(pointToName)) {
			throw new InvalidPointNameException("exceptions.point.required.fromAndTo");
		}
	}
}
