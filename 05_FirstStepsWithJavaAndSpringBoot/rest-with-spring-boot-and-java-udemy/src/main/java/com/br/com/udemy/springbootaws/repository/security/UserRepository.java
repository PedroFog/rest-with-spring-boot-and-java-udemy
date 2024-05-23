package com.br.com.udemy.springbootaws.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.com.udemy.springbootaws.model.security.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM User WHERE u.userName = :userName")
	User findByUsername(@Param("userName") String userName);
	

}
