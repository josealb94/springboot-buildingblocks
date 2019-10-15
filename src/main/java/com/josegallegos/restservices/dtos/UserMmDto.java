package com.josegallegos.restservices.dtos;

import java.util.List;

import com.josegallegos.restservices.entities.Order;

public class UserMmDto {

	private Long userid;
	private String username;
	private String firstname;
	private List<Order> order;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}
	
}
