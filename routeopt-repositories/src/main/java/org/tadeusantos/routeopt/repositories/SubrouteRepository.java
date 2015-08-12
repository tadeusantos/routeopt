package org.tadeusantos.routeopt.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tadeusantos.routeopt.domain.Point;
import org.tadeusantos.routeopt.domain.Subroute;

/**
 * MongoDB Repository for subroute documents.
 * 
 * This interface will be used by Spring Data to generate the actual repository at runtime.
 * 
 * @author Tadeu Santos
 *
 */
public interface SubrouteRepository extends MongoRepository<Subroute, String> {
	List<Subroute> findByFromAndTo(Point from, Point to);
}
