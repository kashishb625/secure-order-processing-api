package com.Kashish.secure_order_api.dto;

public class LoginResponse 
{
	String token;

	public LoginResponse(String token)
	{
		this.token=token;
	}
	
	
	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
	
	

}
