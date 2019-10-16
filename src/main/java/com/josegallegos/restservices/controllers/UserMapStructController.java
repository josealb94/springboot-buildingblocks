package com.josegallegos.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.josegallegos.restservices.dtos.UserMsDto;
import com.josegallegos.restservices.entities.User;
import com.josegallegos.restservices.exceptions.UserNotFoundException;
import com.josegallegos.restservices.mappers.UserMapper;
import com.josegallegos.restservices.repositories.UserRepository;

@RestController
@RequestMapping("/mapstruct/users")
public class UserMapStructController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping
	public List<UserMsDto> getAllUserDtos() {
		return userMapper.usersToUserDtos(userRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public UserMsDto getUserDtoById(@PathVariable Long id) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("Usuario no encontrado");
		}
		User user = userOptional.get();
		return userMapper.userToUserDto(user);
	}
}
