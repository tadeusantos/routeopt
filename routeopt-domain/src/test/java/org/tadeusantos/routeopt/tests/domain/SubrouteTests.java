package org.tadeusantos.routeopt.tests.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.tadeusantos.routeopt.domain.Point;
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
		Point from = new Point("xpto");
		double distance = 0d;
		
		new Subroute(from, null, distance);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNewWithToPoint() {
		Point to = new Point("xpto");
		double distance = 0d;
		
		new Subroute(null, to, distance);
	}
	
	@Test
	public void testNewWithBothPoints() {
		String fromName = "xptoFrom";
		String toName = "xptoTo";
		String routeName = String.format("%s -> %s", fromName, toName);
		
		Point from = new Point(fromName);
		Point to = new Point(toName);
		double distance = 10.4d;
		
		Subroute theNewSubroute = new Subroute(from, to, distance);
		assertThat("subrout id should be null", theNewSubroute.getId(), nullValue());
		assertThat(String.format("subroute name should be %s", routeName),
					theNewSubroute.getName(), equalTo(routeName));
		assertThat(String.format("subroute from should be %s", fromName), 
					theNewSubroute.getFrom().getName(), equalTo(fromName));
		assertThat(String.format("subroute to should be %s", toName), 
				theNewSubroute.getTo().getName(), equalTo(toName));
		assertThat(String.format("subroute distance should be %s", distance), 
				theNewSubroute.getDistance(), equalTo(distance));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNewWithNegativeDistance() {
		String fromName = "xptoFrom";
		String toName = "xptoTo";

		Point from = new Point(fromName);
		Point to = new Point(toName);
		double distance = -1;
		
		new Subroute(from, to, distance);
	}
}
