package com.br.com.udemy.springbootaws.services.security;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.com.udemy.springbootaws.controller.person.PersonController;
import com.br.com.udemy.springbootaws.exceptions.RequiredObjectIsNullException;
import com.br.com.udemy.springbootaws.exceptions.ResourceNotFoundException;
import com.br.com.udemy.springbootaws.mapper.DozerMapper;
import com.br.com.udemy.springbootaws.model.person.Person;
import com.br.com.udemy.springbootaws.repository.person.PersonRepository;
import com.br.com.udemy.springbootaws.repository.security.UserRepository;
import com.br.com.udemy.springbootaws.vo.v1.PersonVO;

@Service
public class UserService implements UserDetailsService {

	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		var personVO = DozerMapper.parseObject(entity, PersonVO.class);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");
		var user = repository.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username: " + username + " not found!");
		}
	}

}
