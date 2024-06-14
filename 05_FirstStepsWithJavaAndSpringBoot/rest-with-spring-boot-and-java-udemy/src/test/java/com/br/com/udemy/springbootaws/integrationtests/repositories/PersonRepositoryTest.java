package com.br.com.udemy.springbootaws.integrationtests.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.com.udemy.springbootaws.integrationtests.testcontainers.AbstractIntegrationTest;
import com.br.com.udemy.springbootaws.model.person.Person;
import com.br.com.udemy.springbootaws.repository.person.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	public PersonRepository repository;
	
	private static Person person;
	
	@BeforeAll
	public static void setup() {
		person = new Person();
	}
	
	@Test
	@Order(1)
	public void testFindByName() throws JsonMappingException, JsonProcessingException {

		Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "firstName"));
		person = repository.findPersonsByName("Bethena", pageable).getContent().get(0);
		assertNotNull(person.getId());
		assertNotNull(person.getFirstName());
		assertNotNull(person.getLastName());
		assertNotNull(person.getAddress());
		assertNotNull(person.getGender());
		assertTrue(person.getEnabled());
		
		assertEquals(218, person.getId());

		assertEquals("Bethena", person.getFirstName());
		assertEquals("Petrecz", person.getLastName());
		assertEquals("5 Bay Trail", person.getAddress());
		assertEquals("Female", person.getGender());
	}
	
	@Test
	@Order(2)
	public void testDisablePerson() throws JsonMappingException, JsonProcessingException {
		
		repository.disablePerson(person.getId());
		
		Pageable pageable = PageRequest.of(0, 6, Sort.by(Direction.ASC, "firstName"));
		person = repository.findPersonsByName("Bethena", pageable).getContent().get(0);
		assertNotNull(person.getId());
		assertNotNull(person.getFirstName());
		assertNotNull(person.getLastName());
		assertNotNull(person.getAddress());
		assertNotNull(person.getGender());
		assertFalse(person.getEnabled());
		
		assertEquals(218, person.getId());

		assertEquals("Bethena", person.getFirstName());
		assertEquals("Petrecz", person.getLastName());
		assertEquals("5 Bay Trail", person.getAddress());
		assertEquals("Female", person.getGender());
	}
	
	

}
