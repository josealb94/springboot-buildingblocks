package com.josegallegos.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.josegallegos.restservices.entities.User;
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
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	//FindUserById
	@GetMapping("/users/{id}")
	public Optional<User> findById(@PathVariable("id") Long id) {
		return userService.findUserById(id);
	}
	
	//UpdateUserById
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		return userService.updateUserById(id, user);
	}
	
	//DeleteUserById
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}
	
	//FindUserByUsername
	@GetMapping("/users/byusername/{username}")
	public User findByUsername(@PathVariable("username") String username) {
		return userService.findUserByUsername(username);
	}
}
