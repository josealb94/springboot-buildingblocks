package com.josegallegos.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josegallegos.restservices.entities.User;
import com.josegallegos.restservices.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	} 
	
	public User createUser(User u) {
		return userRepository.save(u);
	}
	
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
	
	public User updateUserById(Long id, User u) {
		u.setId(id);
		return userRepository.save(u);
	}
	
	public void deleteUserById(Long id) {
		if(userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		}
	}
	
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
