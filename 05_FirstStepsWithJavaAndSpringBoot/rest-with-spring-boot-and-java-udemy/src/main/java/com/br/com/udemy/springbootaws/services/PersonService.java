package com.br.com.udemy.springbootaws.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.com.udemy.springbootaws.exceptions.ResourceNotFoundException;
import com.br.com.udemy.springbootaws.mapper.DozerMapper;
import com.br.com.udemy.springbootaws.mapper.custom.PersonMapper;
import com.br.com.udemy.springbootaws.model.Person;
import com.br.com.udemy.springbootaws.repository.PersonRepository;
import com.br.com.udemy.springbootaws.vo.v1.PersonVO;
import com.br.com.udemy.springbootaws.vo.v2.PersonVOV2;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PersonMapper mapper;

	public List<PersonVO> findAll() {
		logger.info("Finding all people");
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		return DozerMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO create(PersonVO person) {
		logger.info("Creating a person");
		var entity = DozerMapper.parseObject(person, Person.class);
		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
	}

	public PersonVO update(PersonVO person) {
		logger.info("Updating a person");
		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
	}

	public void delete(Long id) {
		logger.info("Removing one person");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.delete(entity);
	}

	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating a person V2");
		var entity = mapper.convertVoToEntity(person);
		return mapper.convertEntityToVO(repository.save(entity));
	}

}
