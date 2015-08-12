package org.tadeusantos.routeopt.tests.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.tadeusantos.routeopt.domain.Subroute;

public class SubrouteTests {

	@Test
	public void testNew() {
		Subroute theNewSubroute = new Subroute();
		assertThat("subrout id should be null", theNewSubroute.getId(), nullValue());
		assertThat("subroute name should be null", theNewSubroute.getName(), nullValue());
		assertThat("subroute from should be null", theNewSubroute.getFrom(), nullValue());
		assertThat("subroute to should be null", theNewSubroute.getTo(), nullValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNewWithFromPoint() {
		String from = "xpto";
		double distance = 0d;
		
		new Subroute("map1", from, null, distance);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNewWithToPoint() {
		String to = "xpto";
		double distance = 0d;
		
		new Subroute("map1", null, to, distance);
	}
	
	@Test
	public void testNewWithBothPoints() {
		String from = "xptoFrom";
		String to = "xptoTo";
		String routeName = String.format("%s -> %s", from, to);
		
		double distance = 10.4d;
		
		Subroute theNewSubroute = new Subroute("map", from, to, distance);
		assertThat("subrout id should be null", theNewSubroute.getId(), nullValue());
		assertThat(String.format("subroute name should be %s", routeName),
					theNewSubroute.getName(), equalTo(routeName));
		assertThat(String.format("subroute from should be %s", from), 
					theNewSubroute.getFrom(), equalTo(from));
		assertThat(String.format("subroute to should be %s", to), 
				theNewSubroute.getTo(), equalTo(to));
		assertThat(String.format("subroute distance should be %s", distance), 
				theNewSubroute.getDistance(), equalTo(distance));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNewWithNegativeDistance() {
		String from = "xptoFrom";
		String to = "xptoTo";

		double distance = -1;
		
		new Subroute("map", from, to, distance);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNewWithoutMap() {
		String from = "xptoFrom";
		String to = "xptoTo";

		double distance = 1;
		
		new Subroute(null, from, to, distance);
	}
}
