package org.tadeusantos.routeopt.services.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tadeusantos.routeopt.domain.Point;
import org.tadeusantos.routeopt.repositories.PointRepository;
import org.tadeusantos.routeopt.services.contract.IPointManagementServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedPointException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;
import org.tadeusantos.routeopt.services.exceptions.PointNotFoundException;

/**
 * Point creating and maintenance.
 * 
 * 
 * @author Tadeu Santos
 *
 */
@Service
public class PointManagementServices implements IPointManagementServices {

	@Autowired
	private PointRepository repository;
	
	public PointManagementServices() {
	}

	@Override
	public Point create(String name) throws DuplicatedPointException, InvalidPointNameException {
		if (StringUtils.isEmpty(name)) {
			throw new InvalidPointNameException("exceptions.point.required.name");
		}
			
		if (!repository.findByName(name).isEmpty()) {
			throw new DuplicatedPointException("exceptions.point.duplicated");
		}
	
		return repository.save(new Point(name));
	}

	@Override
	public List<Point> listAll() {
		return repository.findAll();
	}

	@Override
	public Point get(String name) throws AmbiguousCritereaException {
		List<Point> points = repository.findByName(name);
		
		if (points.size() > 1) {
			throw new AmbiguousCritereaException("exceptions.point.ambiguous");
		}
		if (points.isEmpty()) {
			return null;
		}
		
		return points.get(0);
	}

	@Override
	public void delete(String name) throws AmbiguousCritereaException, PointNotFoundException {
		Point point = get(name);
		
		if (point == null) {
			throw new PointNotFoundException();
		}
		
		repository.delete(point);
	}

}
