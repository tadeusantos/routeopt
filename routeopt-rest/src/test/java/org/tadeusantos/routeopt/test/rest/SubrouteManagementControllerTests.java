package org.tadeusantos.routeopt.test.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.tadeusantos.routeopt.domain.Subroute;
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
public class SubrouteManagementControllerTests {

	@Autowired
	private IUnitTestsHelperServices helperServices;
	@Autowired
	private IPointManagementServices pointServices;
	@Autowired
	private ISubrouteManagementServices subrouteServices;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private Subroute subroute;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Before
	public void reset() throws DuplicatedPointException, InvalidPointNameException, AmbiguousCritereaException, DuplicatedSubrouteException {
		helperServices.deleteAllPoints();
		helperServices.deleteAllSubroutes();

		pointServices.create("A");
		pointServices.create("B");
		pointServices.create("C");
		
		subroute = subrouteServices.create("A", "B", 12.2);
	}

	@Test
	public void testListAll() throws Exception {
		mockMvc.perform(get("/subroutes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"))
				.andExpect(jsonPath("$.['result'].[0].['name']").value("A -> B"));
	}
	
	@Test
	public void testCreate() throws Exception {
		mockMvc.perform(post("/subroutes/B/C/2.3"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"))
				.andExpect(jsonPath("$.['result'].['name']").value("B -> C"));
	}
	
	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete(String.format("/subroutes/%s", subroute.getId())))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"));
	}
}
