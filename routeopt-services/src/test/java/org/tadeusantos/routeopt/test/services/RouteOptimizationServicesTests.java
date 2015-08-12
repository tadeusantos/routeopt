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
import org.tadeusantos.routeopt.domain.Subroute;
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
	private SubrouteRepository subrouteRepository;

	@Autowired
	private IRouteOptimizationServices services;
	
	private String a;
	private String b;
	private String c;
	private String d;
	private String e;

	@Before
	public void reset() {
		subrouteRepository.deleteAll();

		createPoints();
		createSubroutes();
	}
	
	@Test
	public void testOptimize() throws AmbiguousCritereaException {
		OptimizedRoute route = services.optimize("map", a, d, 10, 2.5);
		
		assertThat("the optimized route should not be null", route, notNullValue());
		assertThat("the optimized route should start at A", route.getFrom(), equalTo(a));
		assertThat("the optimized route should end at D", route.getTo(), equalTo(d));
		assertThat("the optimized route cost should be 6.5", route.getEstimatedCost(), equalTo(6.25));
		assertThat("the optimized route distance should be 25.0", route.getEstimatedDistance(), equalTo(25d));
		assertThat("the optimized route distance should be A B D", route.getRoute(), equalTo("A B D"));
	}

	private void createSubroutes() {
		subrouteRepository.save(new Subroute("map", a, b, 10));
		subrouteRepository.save(new Subroute("map", b, d, 15));
		subrouteRepository.save(new Subroute("map", a, c, 20));
		subrouteRepository.save(new Subroute("map", c, d, 30));
		subrouteRepository.save(new Subroute("map", b, e, 50));
		subrouteRepository.save(new Subroute("map", d, e, 30));
	}

	private void createPoints() {
		a = "A";
		b = "B";
		c = "C";
		d = "D";
		e = "E";
	}

}
