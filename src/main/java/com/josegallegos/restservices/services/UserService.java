package com.josegallegos.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.josegallegos.restservices.entities.User;
import com.josegallegos.restservices.exceptions.UserExistsException;
import com.josegallegos.restservices.exceptions.UserNotFoundException;
import com.josegallegos.restservices.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createUser(User u) throws UserExistsException {
		User existingUser = userRepository.findByUsername(u.getUsername());
		if (existingUser != null) {
			throw new UserExistsException("User already exists in repository");
		}
		return userRepository.save(u);
	}

	public Optional<User> findUserById(Long id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User Not foud in user Repository");
		}
		return user;
	}

	public User updateUserById(Long id, User u) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not foud in user Repository, provide the correct user id");
		}
		u.setUserid(id);
		return userRepository.save(u);
	}

	public void deleteUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"User Not foud in user Repository, provide the correct user id");
		}
		userRepository.deleteById(id);
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
