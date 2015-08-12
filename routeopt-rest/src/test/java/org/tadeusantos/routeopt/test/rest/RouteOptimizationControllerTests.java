package org.tadeusantos.routeopt.test.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tadeusantos.routeopt.rest.config.SpringMvcConfiguration;
import org.tadeusantos.routeopt.services.contract.IPointManagementServices;
import org.tadeusantos.routeopt.services.contract.ISubrouteManagementServices;
import org.tadeusantos.routeopt.services.contract.IUnitTestsHelperServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedPointException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedSubrouteException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes = { SpringMvcConfiguration.class })
public class RouteOptimizationControllerTests {

	@Autowired
	private IUnitTestsHelperServices helperServices;
	@Autowired
	private IPointManagementServices pointServices;
	@Autowired
	private ISubrouteManagementServices subrouteServices;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Before
	public void reset() throws DuplicatedPointException, InvalidPointNameException, AmbiguousCritereaException,
			DuplicatedSubrouteException {
		helperServices.deleteAllPoints();
		helperServices.deleteAllSubroutes();

		pointServices.create("A");
		pointServices.create("B");
		pointServices.create("C");
		pointServices.create("D");
		pointServices.create("E");
		pointServices.create("F");

		subrouteServices.create("A", "B", 10);
		subrouteServices.create("B", "D", 15);
		subrouteServices.create("A", "C", 20);
		subrouteServices.create("C", "D", 30);
		subrouteServices.create("B", "E", 50);
		subrouteServices.create("D", "E", 30);
	}

	@Test
	public void testOptimize() throws Exception {
		mockMvc.perform(get("/optimize/A/D/10/2.5"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"))
				.andExpect(jsonPath("$.['result'].['route']").value("A B D"))
				.andExpect(jsonPath("$.['result'].['estimatedCost']").value(6.25));
	}
}
