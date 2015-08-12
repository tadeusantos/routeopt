package org.tadeusantos.routeopt.test.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PointManagementControllerTests.class, RouteOptimizationControllerTests.class,
		SubrouteManagementControllerTests.class })
public class RestTestSuite {

}
