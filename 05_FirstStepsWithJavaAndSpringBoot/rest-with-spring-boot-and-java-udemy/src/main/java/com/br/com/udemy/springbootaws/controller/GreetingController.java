package com.br.com.udemy.springbootaws.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.com.udemy.springbootaws.Greeting;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private static final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue= "World") String name) {
		List<String> teste = new ArrayList<>();
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	
	
}
