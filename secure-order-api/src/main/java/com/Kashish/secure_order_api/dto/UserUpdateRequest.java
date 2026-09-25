package com.Kashish.secure_order_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserUpdateRequest 
{

	@NotBlank(message="Enter a password")
	@Size(min=6,message="Password must be of atleast 6 characters")
	private String password;
	
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
	

}
