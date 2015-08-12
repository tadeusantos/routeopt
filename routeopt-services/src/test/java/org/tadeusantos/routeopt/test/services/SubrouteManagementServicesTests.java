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

	private String to;
	private String from;
	
	@Before
    public void reset() {
		repository.deleteAll();
		
		from = "pointFrom";
		to = "pointTo";
    }
    
	@Test
	public void testNew() throws InvalidPointNameException, AmbiguousCritereaException, 
									DuplicatedSubrouteException {
		Subroute theNewSubroute = services.create("map", from, to, 13.4);

		assertThat("the subroute should not be null", theNewSubroute, notNullValue());
		assertThat(String.format("the from point name should be %s", from), 
					theNewSubroute.getFrom(), equalTo(from));
		assertThat(String.format("the to point name should be %s", to), 
					theNewSubroute.getTo(), equalTo(to));
	}
    
	@Test(expected=DuplicatedSubrouteException.class)
	public void testNewDuplicated() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		services.create("map", from, to, 1000d);
		services.create("map", to, from, 1000d);
	}

	@Test(expected=InvalidPointNameException.class)
	public void testNewNullFromName() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		services.create("map", null, to, 10.1);
	}
	
	@Test(expected=InvalidPointNameException.class)
	public void testNewEmptyToName() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		services.create("map", from, null, 2.12);
	}
	
	@Test()
	public void testDelete() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException, SubrouteNotFoundException {
		int previousTotal = services.listAll(null).size();

		Subroute theNewSubroute = services.create("map", from, to, 13.4);
		assertThat("the subroute should not be null", theNewSubroute, notNullValue());
		
		services.delete(theNewSubroute.getId());
		
		assertThat("the subroute should be gone", previousTotal, equalTo(services.listAll(null).size()));
	}
	
	@Test(expected=SubrouteNotFoundException.class)
	public void testDeleteInexistent() throws AmbiguousCritereaException, SubrouteNotFoundException  {
		services.delete("foo");
	}
	
	@Test()
	public void testListAll() throws AmbiguousCritereaException, DuplicatedSubrouteException, InvalidPointNameException {
		int previousTotal = services.listAll(null).size();

		Subroute theNewSubroute = services.create("map", from, to, 13.4);
		assertThat("the subroute should not be null", theNewSubroute, notNullValue());
		
		assertThat("the subroute should be gone", previousTotal + 1, equalTo(services.listAll(null).size()));

	}
}
