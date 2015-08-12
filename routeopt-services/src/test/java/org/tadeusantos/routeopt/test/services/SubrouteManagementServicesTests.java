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
import org.tadeusantos.routeopt.services.contract.ISubrouteManagementServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedSubrouteException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;
import org.tadeusantos.routeopt.services.exceptions.SubrouteNotFoundException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ServicesConfiguration.class, RepositoriesConfiguration.class })
public class SubrouteManagementServicesTests {

	@Autowired
	private ISubrouteManagementServices services;
	@Autowired
	private SubrouteRepository repository;
	@Autowired
	private PointRepository pointRepository;
	
	private Point to;
	private Point from;
	
	@Before
    public void reset() {
		repository.deleteAll();
		pointRepository.deleteAll();
		
		from = new Point("pointFrom");
		pointRepository.save(from);
		to = new Point("pointTo");
		pointRepository.save(to);
    }
    
	@Test
	public void testNew() throws InvalidPointNameException, AmbiguousCritereaException, 
									DuplicatedSubrouteException {
		Subroute theNewSubroute = services.create(from.getName(), to.getName(), 13.4);

		assertThat("the subroute should not be null", theNewSubroute, notNullValue());
		assertThat(String.format("the from point name should be %s", from.getName()), 
					theNewSubroute.getFrom().getName(), equalTo(from.getName()));
		assertThat(String.format("the to point name should be %s", to.getName()), 
					theNewSubroute.getTo().getName(), equalTo(to.getName()));
	}
    
	@Test(expected=DuplicatedSubrouteException.class)
	public void testNewDuplicated() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		services.create(from.getName(), to.getName(), 1000d);
		services.create(to.getName(), from.getName(), 1000d);
	}

	@Test(expected=InvalidPointNameException.class)
	public void testNewNullFromName() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		services.create(null, to.getName(), 10.1);
	}
	
	@Test(expected=InvalidPointNameException.class)
	public void testNewEmptyToName() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		services.create(from.getName(), null, 2.12);
	}
	
	@Test()
	public void testDelete() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException, SubrouteNotFoundException {
		int previousTotal = services.listAll().size();

		Subroute theNewSubroute = services.create(from.getName(), to.getName(), 13.4);
		assertThat("the subroute should not be null", theNewSubroute, notNullValue());
		
		services.delete(theNewSubroute.getId());
		
		assertThat("the subroute should be gone", previousTotal, equalTo(services.listAll().size()));
	}
	
	@Test(expected=SubrouteNotFoundException.class)
	public void testDeleteInexistent() throws AmbiguousCritereaException, SubrouteNotFoundException  {
		services.delete("foo");
	}
	
	@Test()
	public void testListAll() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		int previousTotal = services.listAll().size();

		Subroute theNewSubroute = services.create(from.getName(), to.getName(), 13.4);
		assertThat("the subroute should not be null", theNewSubroute, notNullValue());
		
		assertThat("the subroute should be gone", previousTotal + 1, equalTo(services.listAll().size()));

	}
}
