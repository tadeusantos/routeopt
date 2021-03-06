package org.tadeusantos.routeopt.test.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tadeusantos.routeopt.domain.Subroute;
import org.tadeusantos.routeopt.repositories.SubrouteRepository;
import org.tadeusantos.routeopt.repositories.config.RepositoriesConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RepositoriesConfiguration.class)
public class SubrouteRepositoryTests {

	@Autowired
	private SubrouteRepository repository;
	
	private String from;
	private String to;
	
	@Before
    public void reset() {
		repository.deleteAll();
		
		from = "fromP";
		to = "fromT";
    }
    
	@Test
	public void testFindByFromAndToMatch() {
		Subroute theNewSubroute = repository.save(new Subroute("map", from, to, 10));

		assertThat("the subroute id should not be null", theNewSubroute.getId(), notNullValue());

		List<Subroute> retrievedSubroutes = repository.findByMapAndFromAndTo("map", from, to);
		assertThat("the subroute list should not be null", retrievedSubroutes, notNullValue());
		assertThat("the subroute list should not be empty", retrievedSubroutes, not(empty()));
	}
	
	@Test
	public void testFindByFromNameMiss() {
		Subroute theNewSubroute = repository.save(new Subroute("map", from, to, 10));

		assertThat("the subroute id should not be null", theNewSubroute.getId(), notNullValue());

		List<Subroute> retrievedSubroutes = repository.findByMapAndFromAndTo("map", to, from);
		assertThat("the subroute list should not be null", retrievedSubroutes, notNullValue());
		assertThat("the subroute list should be empty", retrievedSubroutes, empty());
	}

}
