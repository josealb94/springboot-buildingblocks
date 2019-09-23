package com.josegallegos.restservices.Hello;

public class UserDetails {
	private String firstname;
	private String lastname;
	private String ciy;
	
	//Fields Constructor
	public UserDetails(String firstname, String lastname, String ciy) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.ciy = ciy;
	}
	
	//Getters and Setters
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCiy() {
		return ciy;
	}
	public void setCiy(String ciy) {
		this.ciy = ciy;
	}

	//To String
	@Override
	public String toString() {
		return "UserDetails [firstname=" + firstname + ", lastname=" + lastname + ", ciy=" + ciy + "]";
	}	
	
}
