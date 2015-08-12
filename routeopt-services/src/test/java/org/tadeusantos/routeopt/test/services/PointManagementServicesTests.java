package org.tadeusantos.routeopt.test.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tadeusantos.routeopt.domain.Point;
import org.tadeusantos.routeopt.repositories.PointRepository;
import org.tadeusantos.routeopt.repositories.config.RepositoriesConfiguration;
import org.tadeusantos.routeopt.services.config.ServicesConfiguration;
import org.tadeusantos.routeopt.services.contract.IPointManagementServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedPointException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;
import org.tadeusantos.routeopt.services.exceptions.PointNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ServicesConfiguration.class, RepositoriesConfiguration.class })
public class PointManagementServicesTests {

	@Autowired
	private IPointManagementServices services;
	@Autowired
	private PointRepository repository;
	
	@Before
    public void reset() {
		repository.deleteAll();
		
		repository.save(new Point("duplicated1"));
		repository.save(new Point("duplicated1"));
    }
    
	@Test
	public void testNew() throws DuplicatedPointException, InvalidPointNameException {
		String pointName = "xpto";
		
		Point theNewPoint = services.create(pointName);

		assertThat("the point id should not be null", theNewPoint.getId(), notNullValue());
		assertThat(String.format("the point name should be %s", pointName), theNewPoint.getName(), equalTo(pointName));
	}
    
	@Test(expected=DuplicatedPointException.class)
	public void testNewDuplicated() throws DuplicatedPointException, InvalidPointNameException {
		String pointName = "xpto";
		
		services.create(pointName);
		services.create(pointName);
	}

	@Test(expected=InvalidPointNameException.class)
	public void testNewNullName() throws DuplicatedPointException, InvalidPointNameException {
		String pointName = null;

		services.create(pointName);
	}
	
	@Test(expected=InvalidPointNameException.class)
	public void testNewEmptyName() throws DuplicatedPointException, InvalidPointNameException {
		String pointName = "";

		services.create(pointName);
	}
	
	@Test()
	public void testDelete() throws DuplicatedPointException, InvalidPointNameException, AmbiguousCritereaException, PointNotFoundException {
		String pointName = "xpto";

		Point theNewPoint = services.create(pointName);
		assertThat("the point id should not be null", theNewPoint.getId(), notNullValue());
		
		services.delete(pointName);
		Point retrievedPoint = services.get(pointName);
		assertThat("the point should be gone", retrievedPoint, nullValue());
	}
	
	@Test(expected=PointNotFoundException.class)
	public void testDeleteInexistent() throws DuplicatedPointException, InvalidPointNameException, AmbiguousCritereaException, PointNotFoundException {
		String pointName = "xpto";

		Point theNewPoint = services.create(pointName);
		assertThat("the point id should not be null", theNewPoint.getId(), notNullValue());
		
		services.delete("foo");
	}
	
	@Test()
	public void testGet() throws DuplicatedPointException, InvalidPointNameException, AmbiguousCritereaException, PointNotFoundException {
		String pointName = "xpto";

		Point theNewPoint = services.create(pointName);
		assertThat("the point id should not be null", theNewPoint.getId(), notNullValue());
		
		Point retrievedPoint = services.get(pointName);
		assertThat("the point should be found", retrievedPoint, notNullValue());
	}

	@Test(expected=AmbiguousCritereaException.class)
	public void testGetDuplicated() throws DuplicatedPointException, AmbiguousCritereaException {
		services.get("duplicated1");
	}
}
