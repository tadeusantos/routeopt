package org.tadeusantos.routeopt.test.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PointManagementServicesTests.class, RouteOptimizationServicesTests.class,
		SubrouteManagementServicesTests.class })
public class ServicesTestSuite {

}
