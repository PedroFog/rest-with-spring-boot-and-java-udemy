package com.br.com.udemy.springbootaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.com.udemy.springbootaws.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
