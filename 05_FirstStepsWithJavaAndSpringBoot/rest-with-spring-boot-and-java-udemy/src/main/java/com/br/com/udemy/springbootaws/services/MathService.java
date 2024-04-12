package com.br.com.udemy.springbootaws.services;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.br.com.udemy.springbootaws.exceptions.UnsupportedMathOperationException;
import com.br.com.udemy.springbootaws.util.FunctionsUtils;

@Service
public class MathService {

	private final static String DEFAULT_MESSAGE = "Please set a numeric value";

	public Double sum(String numberOne, String numberTwo) {
		return calculateValues(numberOne, numberTwo, (n1, n2) -> n1 + n2);
	}

	public Double subtract(String numberOne, String numberTwo) {
		return calculateValues(numberOne, numberTwo, (n1, n2) -> n1 - n2);
	}

	public Double multiply(String numberOne, String numberTwo) {
		return calculateValues(numberOne, numberTwo, (n1, n2) -> n1 * n2);
	}

	public Double divide(String numberOne, String numberTwo) {
		return calculateValues(numberOne, numberTwo, (n1, n2) -> n1 / n2);
	}

	public Double avg(String numberOne, String numberTwo) {
		return calculateValues(numberOne, numberTwo, (n1, n2) -> ((n1 + n2) / 2));
	}

	public Double root(String numberOne) {
		return calculateValueSingle(numberOne, Math::sqrt);
	}

	private void validateIsNumeric(String numberOne, String numberTwo) {
		if (!FunctionsUtils.isNumeric(numberOne) || (!FunctionsUtils.isNumeric(numberTwo))) {
			throw new UnsupportedMathOperationException(DEFAULT_MESSAGE);
		}
	}

	public Double calculateValues(String numberOne, String numberTwo, BiFunction<Double, Double, Double> operation) {
		validateIsNumeric(numberOne, numberTwo);
		return operation.apply(FunctionsUtils.convertToDouble(numberOne), FunctionsUtils.convertToDouble(numberTwo));
	}

	public Double calculateValueSingle(String number, Function<Double, Double> operation) {
		validateIsNumeric(number, "0");
		return operation.apply(FunctionsUtils.convertToDouble(number));
	}

}
