package org.tadeusantos.routeopt.test.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tadeusantos.routeopt.domain.Subroute;
import org.tadeusantos.routeopt.rest.config.SpringMvcConfiguration;
import org.tadeusantos.routeopt.rest.request.CreateMapRequest;
import org.tadeusantos.routeopt.services.contract.ISubrouteManagementServices;
import org.tadeusantos.routeopt.services.contract.IUnitTestsHelperServices;
import org.tadeusantos.routeopt.services.exceptions.AmbiguousCritereaException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedPointException;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedSubrouteException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes = { SpringMvcConfiguration.class })
public class MapControllerTests {

	@Autowired
	private IUnitTestsHelperServices helperServices;
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
		helperServices.deleteAllSubroutes();
		subroute = subrouteServices.create("maps", "A", "B", 12.2);
	}

	@Test
	public void testListAll() throws Exception {
		mockMvc.perform(get("/map"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"))
				.andExpect(jsonPath("$.['result'].[0].['name']").value("A -> B"));
	}
	
	@Test
	public void testGet() throws Exception {
		mockMvc.perform(get(String.format("/map/%s", "maps")))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.['status']").value("OK"))
		.andExpect(jsonPath("$.['result'].[0].['name']").value("A -> B"));
	}
	
	@Test
	public void testCreate() throws Exception {
		CreateMapRequest request = new CreateMapRequest();
		request.setName("Test");
		request.setRoutes("A B 10\nB D 15\nA C 20\n\nC D 30\nB E 50\nD E 30");
		
		mockMvc.perform(
					post("/map")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(new Gson().toJson(request))
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"));
	}
	
	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete(String.format("/map/%s", subroute.getId())))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"));
	}
}
