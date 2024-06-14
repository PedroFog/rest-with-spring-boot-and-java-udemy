package com.br.com.udemy.springbootaws.services.person;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.com.udemy.springbootaws.controller.person.PersonController;
import com.br.com.udemy.springbootaws.exceptions.RequiredObjectIsNullException;
import com.br.com.udemy.springbootaws.exceptions.ResourceNotFoundException;
import com.br.com.udemy.springbootaws.mapper.DozerMapper;
import com.br.com.udemy.springbootaws.model.person.Person;
import com.br.com.udemy.springbootaws.repository.person.PersonRepository;
import com.br.com.udemy.springbootaws.vo.v1.PersonVO;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	@Autowired
	private PersonRepository repository;

	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;

	public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {
		logger.info("Finding all people");

		var personPage = repository.findAll(pageable);

		var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		Link link = linkTo(
				methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(personVosPage, link);
	}
	
	public PagedModel<EntityModel<PersonVO>> findPersonsByName(String firstName, Pageable pageable) {
		logger.info("Finding all people");
		
		var personPage = repository.findPersonsByName(firstName, pageable);
		
		var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(
				methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(personVosPage, link);
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		var personVO = DozerMapper.parseObject(entity, PersonVO.class);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	public PersonVO create(PersonVO person) {

		if (person == null)
			throw new RequiredObjectIsNullException();

		logger.info("Creating a person");
		var entity = DozerMapper.parseObject(person, Person.class);
		var personVO = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	public PersonVO update(PersonVO person) {
		if (person == null)
			throw new RequiredObjectIsNullException();

		logger.info("Updating a person");
		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var personVO = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	@Transactional
	public PersonVO disablePerson(Long id) {
		logger.info("Disabling one person");
		repository.disablePerson(id);

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		var personVO = DozerMapper.parseObject(entity, PersonVO.class);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	public void delete(Long id) {
		logger.info("Removing one person");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.delete(entity);
	}

//	public PersonVOV2 createV2(PersonVOV2 person) {
//		logger.info("Creating a person V2");
//		var entity = mapper.convertVoToEntity(person);
//		return mapper.convertEntityToVO(repository.save(entity));
//	}

}
