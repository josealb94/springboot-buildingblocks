package com.josegallegos.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.josegallegos.restservices.entities.User;
import com.josegallegos.restservices.exceptions.UserExistsException;
import com.josegallegos.restservices.exceptions.UserNotFoundException;
import com.josegallegos.restservices.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//FindAll
	@GetMapping("/users")
	public List<User> getAllUser() {
		return userService.getAllUsers();
	}
	
	//CreateUser
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder builder) {
		try {
			userService.createUser(user);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}
		catch (UserExistsException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	//FindUserById
	@GetMapping("/users/{id}")
	public Optional<User> findById(@PathVariable("id") Long id) {
		try {
			return userService.findUserById(id);
		}
		catch(UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	//UpdateUserById
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			return userService.updateUserById(id, user);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
		
	}
	
	//DeleteUserById
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}
	
	//FindUserByUsername
	@GetMapping("/users/byusername/{username}")
	public User findByUsername(@PathVariable("username") String username) {
		try {
			return userService.findUserByUsername(username);
		}
		catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
}
