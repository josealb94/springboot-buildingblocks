package com.josegallegos.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.josegallegos.restservices.entities.Order;
import com.josegallegos.restservices.entities.User;
import com.josegallegos.restservices.exceptions.UserNotFoundException;
import com.josegallegos.restservices.repositories.UserRepository;
import com.josegallegos.restservices.services.UserService;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public Resources<User> getAllUser() throws UserNotFoundException {
		List<User> allusers = userService.getAllUsers();
		
		for(User user : allusers) {
			Long userid = user.getUserid();
			Link selflink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selflink);
			
			/*
			List<Order> allorders = user.getOrder();
			for(Order order : allorders) {
				Long orderid = order.getOrderid();
				ControllerLinkBuilder.linkTo(OrderHateoasController.class).slash(orderid).withRel(orderid.toString());
			}
			*/
			Resources<Order> orders = ControllerLinkBuilder.methodOn(OrderHateoasController.class)
					.getAllOrders(userid);
			Link orderslink = ControllerLinkBuilder.linkTo(orders).withRel("all-orders");
			user.add(orderslink);
		}
		
		Link selflinkgetAllUsers = ControllerLinkBuilder.linkTo(this.getClass()).withSelfRel();
		Resources<User> finalResource = new Resources<User>(allusers, selflinkgetAllUsers);
		return finalResource;
	}
	
	@GetMapping("/{id}")
	public Resource<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		try {
			Optional<User> userOptional = userService.findUserById(id);
			
			User user = userOptional.get();
			Long userid = user.getUserid();
			Link selflink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selflink);
			Resource<User> finalResource = new Resource<User>(user);
			return finalResource;
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
}
