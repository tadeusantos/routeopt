package org.tadeusantos.routeopt.tests.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.tadeusantos.routeopt.domain.Point;

public class PointTests {

	@Test
	public void testNew() {
		Point theNewPoint = new Point();
		assertThat("point id should be null", theNewPoint.getId(), nullValue());
		assertThat("point name should be null", theNewPoint.getName(), nullValue());
	}
	
	@Test
	public void testNewWithName() {
		String pointName = "xpto";
		
		Point theNewPoint = new Point(pointName);
		assertThat("point id should be null", theNewPoint.getId(), nullValue());
		assertThat(String.format("point name should be %s", pointName), theNewPoint.getName(), equalTo(pointName));
	}

}
