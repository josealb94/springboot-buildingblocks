package com.josegallegos.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.josegallegos.restservices.entities.Order;
import com.josegallegos.restservices.entities.User;
import com.josegallegos.restservices.exceptions.UserNotFoundException;
import com.josegallegos.restservices.repositories.OrderRepository;
import com.josegallegos.restservices.repositories.UserRepository;

@RestController
@RequestMapping(value = "/hateoas/users")
public class OrderHateoasController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@GetMapping("/{userid}/orders")
	public Resources<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent())
			throw new UserNotFoundException("User Not Found");
		
		List<Order> allorder = userOptional.get().getOrder();
		Resources<Order> finalResources = new Resources<Order>(allorder);
		
		return finalResources;
	}
}
