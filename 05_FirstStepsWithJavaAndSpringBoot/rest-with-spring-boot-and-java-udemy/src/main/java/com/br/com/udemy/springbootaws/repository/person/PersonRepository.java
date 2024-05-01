package com.br.com.udemy.springbootaws.repository.person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.com.udemy.springbootaws.model.person.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
	
}
