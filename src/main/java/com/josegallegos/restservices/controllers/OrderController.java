package com.josegallegos.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.josegallegos.restservices.entities.Order;
import com.josegallegos.restservices.entities.User;
import com.josegallegos.restservices.exceptions.OrderNotFoundException;
import com.josegallegos.restservices.exceptions.UserNameNotFoundException;
import com.josegallegos.restservices.exceptions.UserNotFoundException;
import com.josegallegos.restservices.repositories.OrderRepository;
import com.josegallegos.restservices.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class OrderController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping("/{userid}/orders")
	public List<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		return userOptional.get().getOrder();
	}
	
	@PostMapping("/{userid}/orders")
	public Order createOrder(@PathVariable("userid") Long userid, @RequestBody Order order) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		
		User user = userOptional.get();
		order.setUser(user);
		return orderRepository.save(order);
	}

	@GetMapping("/{userid}/orders/{orderid}")
	public Order getOrderByOrderId(@PathVariable("userid") Long userid, @PathVariable("orderid") Long orderid) throws UserNotFoundException, OrderNotFoundException {
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		
		Optional<Order> orderOptional = userOptional.get().getOrder().stream().filter(ord -> ord.getOrderid().equals(orderid)).findFirst(); 
		if(!orderOptional.isPresent()) {
			throw new OrderNotFoundException("Order Not Found");
		}
		return orderOptional.get();
	}
	
	/*
	@GetMapping("/byusername/{username}")
	public User getUserByUsername(@PathVariable("username") String username) throws  UserNameNotFoundException {
		User user = userService.findUserByUsername(username);
		if(user == null)
			throw new UserNameNotFoundException("Username: '"+username+"' not found in User repository");
		return user;
	}
	*/
}
