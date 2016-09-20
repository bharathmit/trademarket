package com.trademarket.model;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


public class UserTransfer
{

	private final String name;
	private String password;

	private final Map<String, Boolean> roles;


	public UserTransfer(String userName, Map<String, Boolean> roles)
	{
		this.name = userName;
		this.roles = roles;
	}


	public String getName()
	{
		return this.name;
	}


	public Map<String, Boolean> getRoles()
	{
		return this.roles;
	}


	public String getPassword() {
		return password;
	}


}