package com.br.com.udemy.springbootaws.controller.math;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.com.udemy.springbootaws.services.math.MathService;

@RestController
public class MathController {

	private final MathService mathService;

	public MathController(MathService mathService) {
		this.mathService = mathService;
	}

	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathService.sum(numberOne, numberTwo);
	}

	@RequestMapping(value = "/subtract/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtract(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathService.subtract(numberOne, numberTwo);
	}

	@RequestMapping(value = "/multiply/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiply(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathService.multiply(numberOne, numberTwo);
	}

	@RequestMapping(value = "/divide/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double divide(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathService.divide(numberOne, numberTwo);
	}

	@RequestMapping(value = "/avg/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double average(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return mathService.avg(numberOne, numberTwo);
	}

	@RequestMapping(value = "/root/{numberOne}", method = RequestMethod.GET)
	public Double average(@PathVariable(value = "numberOne") String numberOne) throws Exception {
		return mathService.root(numberOne);
	}

}
