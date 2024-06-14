package com.br.com.udemy.springbootaws.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.com.udemy.springbootaws.configs.TestConfigs;
import com.br.com.udemy.springbootaws.integrationtests.testcontainers.AbstractIntegrationTest;
import com.br.com.udemy.springbootaws.integrationtests.vo.AccountCredentialsVO;
import com.br.com.udemy.springbootaws.integrationtests.vo.PersonVO;
import com.br.com.udemy.springbootaws.integrationtests.vo.TokenVO;
import com.br.com.udemy.springbootaws.integrationtests.vo.wrappers.WrapperPersonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static PersonVO person;

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();

	}

	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("pedro", "admin123");

		var accessToken = given().basePath("/auth/signin").port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON).body(user).when().post().then().statusCode(200).extract()
				.body().as(TokenVO.class).getAccessToken();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1").setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL)).addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(person).when().post()
				.then().statusCode(200).extract().body().asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());

		assertTrue(persistedPerson.getId() > 0);

		assertEquals("Pedro", persistedPerson.getFirstName());
		assertEquals("Foganholi", persistedPerson.getLastName());
		assertEquals("Assis", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setLastName("Foganholi Update");
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).body(person).when().put()
				.then().statusCode(200).extract().body().asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Pedro", persistedPerson.getFirstName());
		assertEquals("Foganholi Update", persistedPerson.getLastName());
		assertEquals("Assis", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(3)
	public void testDisablePersonById() throws JsonMappingException, JsonProcessingException {
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_UDEMY).pathParam("id", person.getId())
				.when().patch("{id}").then().statusCode(200).extract().body().asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Pedro", persistedPerson.getFirstName());
		assertEquals("Foganholi Update", persistedPerson.getLastName());
		assertEquals("Assis", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(4)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();

		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_UDEMY).pathParam("id", person.getId())
				.when().get("{id}").then().statusCode(200).extract().body().asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Pedro", persistedPerson.getFirstName());
		assertEquals("Foganholi Update", persistedPerson.getLastName());
		assertEquals("Assis", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(5)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON).pathParam("id", person.getId()).when()
				.delete("{id}").then().statusCode(204);
	}

	@Test
	@Order(6)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page", 3, "size", 10, "direction", "asc").when().get().then().statusCode(200).extract()
				.body().asString();

		WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
		var people = wrapper.getEmbedded().getPersons();

		PersonVO foundPersonOne = people.get(0);
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());

		assertEquals(194, foundPersonOne.getId());

		assertEquals("Alfred", foundPersonOne.getFirstName());
		assertEquals("Grant", foundPersonOne.getLastName());
		assertEquals("492 Hermina Junction", foundPersonOne.getAddress());
		assertEquals("Male", foundPersonOne.getGender());

		PersonVO foundPersonSecond = people.get(1);

		assertNotNull(foundPersonSecond.getId());
		assertNotNull(foundPersonSecond.getFirstName());
		assertNotNull(foundPersonSecond.getLastName());
		assertNotNull(foundPersonSecond.getAddress());
		assertNotNull(foundPersonSecond.getGender());

		assertEquals(714, foundPersonSecond.getId());

		assertEquals("Ali", foundPersonSecond.getFirstName());
		assertEquals("Basilotta", foundPersonSecond.getLastName());
		assertEquals("856 Paget Pass", foundPersonSecond.getAddress());
		assertEquals("Female", foundPersonSecond.getGender());
	}

	@Test
	@Order(7)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder().setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT).addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL)).build();

		given().spec(specificationWithoutToken).contentType(TestConfigs.CONTENT_TYPE_JSON).when().get().then()
				.statusCode(403);

	}
	
	@Test
	@Order(8)
	public void testFindByName() throws JsonMappingException, JsonProcessingException {
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("firstName", "Beatr")
				.queryParams("page", 0, "size", 10, "direction", "asc").when().get("name/{firstName}").then().statusCode(200).extract()
				.body().asString();

		WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
		var people = wrapper.getEmbedded().getPersons();

		PersonVO foundPersonOne = people.get(0);
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());
		assertFalse(foundPersonOne.getEnabled());
		assertEquals(546, foundPersonOne.getId());

		assertEquals("Beatrix", foundPersonOne.getFirstName());
		assertEquals("Ivanovic", foundPersonOne.getLastName());
		assertEquals("39 Messerschmidt Park", foundPersonOne.getAddress());
		assertEquals("Female", foundPersonOne.getGender());
		

	}
	
	@Test
	@Order(9)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		var content = given().spec(specification).contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page", 3, "size", 10, "direction", "asc").when().get().then().statusCode(200).extract()
				.body().asString();

		assertTrue(content.contains("_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/920"));
		assertTrue(content.contains("_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/841"));
		assertTrue(content.contains("_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/102"));
		
		assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8888/api/person/v1?direction=asc&page=0&size=10&sort=firstName,asc\"}"));
		assertTrue(content.contains("\"prev\":{\"href\":\"http://localhost:8888/api/person/v1?direction=asc&page=2&size=10&sort=firstName,asc\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/person/v1?page=3&size=10&direction=asc\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/person/v1?direction=asc&page=4&size=10&sort=firstName,asc\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/person/v1?direction=asc&page=100&size=10&sort=firstName,asc\"}}"));
		
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":1003,\"totalPages\":101,\"number\":3}}"));
	}

	private void mockPerson() {
		person.setFirstName("Pedro");
		person.setLastName("Foganholi");
		person.setAddress("Assis");
		person.setGender("Male");
		person.setEnabled(true);

	}

}
