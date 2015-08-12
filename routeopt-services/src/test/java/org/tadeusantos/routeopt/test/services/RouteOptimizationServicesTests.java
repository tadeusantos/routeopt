package org.tadeusantos.routeopt.test.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tadeusantos.routeopt.domain.Point;
import org.tadeusantos.routeopt.domain.Subroute;
import org.tadeusantos.routeopt.repositories.PointRepository;
import org.tadeusantos.routeopt.repositories.SubrouteRepository;
import org.tadeusantos.routeopt.repositories.config.RepositoriesConfiguration;
import org.tadeusantos.routeopt.services.config.ServicesConfiguration;
import org.tadeusantos.routeopt.services.contract.IRouteOptimizationServices;
import org.tadeusantos.routeopt.services.dto.OptimizedRoute;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ServicesConfiguration.class, RepositoriesConfiguration.class })

public class RouteOptimizationServicesTests {

	@Autowired
	private PointRepository pointRepository;
	@Autowired
	private SubrouteRepository subrouteRepository;

	@Autowired
	private IRouteOptimizationServices services;
	
	private Point a;
	private Point b;
	private Point c;
	private Point d;
	private Point e;

	@Before
	public void reset() {
		pointRepository.deleteAll();
		subrouteRepository.deleteAll();

		createPoints();
		createSubroutes();
	}
	
	@Test
	public void testOptimize() throws AmbiguousCritereaException {
		OptimizedRoute route = services.optimize(a.getName(), d.getName(), 10, 2.5);
		
		assertThat("the optimized route should not be null", route, notNullValue());
		assertThat("the optimized route should start at A", route.getFrom().getName(), equalTo(a.getName()));
		assertThat("the optimized route should end at D", route.getTo().getName(), equalTo(d.getName()));
		assertThat("the optimized route cost should be 6.5", route.getEstimatedCost(), equalTo(6.25));
		assertThat("the optimized route distance should be 25.0", route.getEstimatedDistance(), equalTo(25d));
		assertThat("the optimized route distance should be A B D", route.getRoute(), equalTo("A B D"));
	}

	private void createSubroutes() {
		subrouteRepository.save(new Subroute(a, b, 10));
		subrouteRepository.save(new Subroute(b, d, 15));
		subrouteRepository.save(new Subroute(a, c, 20));
		subrouteRepository.save(new Subroute(c, d, 30));
		subrouteRepository.save(new Subroute(b, e, 50));
		subrouteRepository.save(new Subroute(d, e, 30));
	}

	private void createPoints() {
		a = new Point("A");
		pointRepository.save(a);
		b = new Point("B");
		pointRepository.save(b);
		c = new Point("C");
		pointRepository.save(c);
		d = new Point("D");
		pointRepository.save(d);
		e = new Point("E");
		pointRepository.save(e);
	}

}
