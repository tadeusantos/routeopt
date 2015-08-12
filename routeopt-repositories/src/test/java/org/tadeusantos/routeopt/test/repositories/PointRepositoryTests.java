package org.tadeusantos.routeopt.test.repositories;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.empty;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tadeusantos.routeopt.domain.Point;
import org.tadeusantos.routeopt.repositories.PointRepository;
import org.tadeusantos.routeopt.repositories.config.RepositoriesConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RepositoriesConfiguration.class)
public class PointRepositoryTests {
	
	@Autowired
	private PointRepository repository;
	
	@Before
    public void reset() {
		repository.deleteAll();
    }
    
	
	@Test
	public void testFindByNameMatch() {
		String pointName = "xpto";
		Point theNewPoint = repository.save(new Point(pointName));
		assertThat("the point id should not be null", theNewPoint.getId(), notNullValue());
		
		List<Point> retrievedPoint = repository.findByName(pointName);
		assertThat("the point list should not be null", retrievedPoint, notNullValue());
		assertThat("the point list should not be empty", retrievedPoint, not(empty()));
	}

	@Test
	public void testFindByNameMiss() {
		String pointName = "xpto";
		Point theNewPoint = repository.save(new Point(pointName));
		assertThat("the point id should not be null", theNewPoint.getId(), notNullValue());
		
		List<Point> retrievedPoint = repository.findByName("full");
		assertThat("the point list should not be null", retrievedPoint, notNullValue());
		assertThat("the point list should be empty", retrievedPoint, empty());
	}
}
