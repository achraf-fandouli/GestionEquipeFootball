package com.footballteam.managefootballteam.integrationtests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.footballteam.managefootballteam.integrationtests.dto.PaginatedResponse;
import com.footballteam.managefootballteam.integrationtests.repository.TestH2Repository;
import com.footballteam.managefootballteam.model.Equipe;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeControllerTest {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private TestH2Repository h2Repository;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@AfterEach
	void deleteALl() {
		h2Repository.deleteAll();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/equipes");
	}

	@Test
	public void testCreateEquipe() {
		Equipe equipe = new Equipe(1L, "EquipeNameTestIntegration", "eti", 1000L, new ArrayList<>());
		Equipe response = restTemplate.postForObject(baseUrl, equipe, Equipe.class);
		assertEquals("EquipeNameTestIntegration", response.getNom());
		assertEquals(1, h2Repository.findAll().size());
	}

	@Test
	@Sql(statements = {
			"insert into Equipe(nom,acronyme,budget) values('EqAIntegrationTest','EAIT',1000)",
			"insert into Equipe(nom,acronyme,budget) values('EqBIntegrationTest','EBIT',10000)"
		}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindEquipesByGenericSort() {

	    ParameterizedTypeReference<PaginatedResponse<Equipe>> responseType = 
	            new ParameterizedTypeReference<PaginatedResponse<Equipe>>() {};

	 
	    ResponseEntity<PaginatedResponse<Equipe>> responseEntity = restTemplate.exchange(
	            baseUrl + "/genericSort?nom=DESC&acronyme=DESC&budget=ASC",
	            HttpMethod.GET,
	            null,
	            responseType
	    );

	    PaginatedResponse<Equipe> paginatedResponse = responseEntity.getBody();
	    List<Equipe> equipes = paginatedResponse.getContent();

		assertEquals(2, equipes.size());
		assertEquals("EqBIntegrationTest", equipes.get(0).getNom());
	}
	
	@Test
	@Sql(statements = {
			"insert into Equipe(nom,acronyme,budget) values('EqAIntegrationTest','EAIT',1000)",
			"insert into Equipe(nom,acronyme,budget) values('EqBIntegrationTest','EBIT',10000)"
		}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindEquipesBySimpleSort() {

	    ParameterizedTypeReference<PaginatedResponse<Equipe>> responseType = 
	            new ParameterizedTypeReference<PaginatedResponse<Equipe>>() {};

	    ResponseEntity<PaginatedResponse<Equipe>> responseEntity = restTemplate.exchange(
	            baseUrl + "/simpleSort",
	            HttpMethod.GET,
	            null,
	            responseType
	    );

	    PaginatedResponse<Equipe> paginatedResponse = responseEntity.getBody();
	    List<Equipe> equipes = paginatedResponse.getContent();

		assertAll(
				() -> assertEquals(2, equipes.size()),
				() -> assertEquals("EqAIntegrationTest", equipes.get(0).getNom())
		);
	}
	
	/**
	 * test integration of findEquipeById
	 */
	@Test
	@Sql(statements = "insert into Equipe(id,nom,acronyme,budget) values(6,'EqBIntegrationTest','EBIT',10000)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void testFindEquipeById() {
		Equipe equipe = restTemplate.getForObject(baseUrl+"/{id}", Equipe.class,6);
		assertAll(
				() -> assertNotNull(equipe),
				() -> assertEquals(6, equipe.getId())
		);
	}
}
