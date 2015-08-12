package org.tadeusantos.routeopt.test.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.tadeusantos.routeopt.services.contract.IUnitTestsHelperServices;
import org.tadeusantos.routeopt.services.exceptions.DuplicatedPointException;
import org.tadeusantos.routeopt.services.exceptions.InvalidPointNameException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes = { SpringMvcConfiguration.class })
public class PointManagementControllerTests {

	@Autowired
	private IUnitTestsHelperServices helperServices;
	@Autowired
	private IPointManagementServices pointServices;
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Before
	public void reset() throws DuplicatedPointException, InvalidPointNameException {
		helperServices.deleteAllPoints();
		helperServices.deleteAllSubroutes();

		pointServices.create("xpto");
	}

	@Test
	public void testListAll() throws Exception {
		mockMvc.perform(get("/points"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"))
				.andExpect(jsonPath("$.['result'].[0].['name']").value("xpto"));
	}

	@Test
	public void testGet() throws Exception {
		mockMvc.perform(get("/points/xpto"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"))
				.andExpect(jsonPath("$.['result'].['name']").value("xpto"));
	}
	
	@Test
	public void testCreate() throws Exception {
		mockMvc.perform(post("/points/xpto2"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"))
				.andExpect(jsonPath("$.['result'].['name']").value("xpto2"));
	}
	
	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete("/points/xpto"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.['status']").value("OK"));
	}
}
