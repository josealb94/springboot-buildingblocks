package com.josegallegos.restservices.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.josegallegos.restservices.entities.User;
import com.josegallegos.restservices.exceptions.UserNotFoundException;
import com.josegallegos.restservices.services.UserService;

@RestController
@RequestMapping(value = "/jacksonfilter/users")
@Validated
public class UserMappingJacksonController {

	@Autowired
	private UserService userService;

	// Fields with HashSet
	@GetMapping("/{id}")
	public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id) {
		try {
			Optional<User> userOptional = userService.findUserById(id);
			User user = userOptional.get();

			Set<String> fields = new HashSet<String>();
			fields.add("userid");
			fields.add("username");
			fields.add("ssn");
			fields.add("order");
			fields.add("links");

			FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",
					SimpleBeanPropertyFilter.filterOutAllExcept(fields));

			MappingJacksonValue mapper = new MappingJacksonValue(user);
			mapper.setFilters(filterProvider);
			return mapper;

		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	// Fields with @RequestParam
	@GetMapping("/params/{id}")
	public MappingJacksonValue getUserById2(@PathVariable("id") @Min(1) Long id, @RequestParam Set<String> fields) {
		try {
			Optional<User> userOptional = userService.findUserById(id);
			User user = userOptional.get();

			FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",
					SimpleBeanPropertyFilter.filterOutAllExcept(fields));

			MappingJacksonValue mapper = new MappingJacksonValue(user);
			mapper.setFilters(filterProvider);
			return mapper;

		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
}
