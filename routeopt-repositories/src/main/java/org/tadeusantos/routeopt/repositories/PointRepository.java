package org.tadeusantos.routeopt.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.tadeusantos.routeopt.domain.Point;

/**
 * MongoDB Repository for point documents.
 * 
 * This interface will be used by Spring Data to generate the actual repository at runtime.
 * 
 * @author Tadeu Santos
 *
 */
public interface PointRepository extends MongoRepository<Point, String> {
	List<Point> findByName(String name);
}
