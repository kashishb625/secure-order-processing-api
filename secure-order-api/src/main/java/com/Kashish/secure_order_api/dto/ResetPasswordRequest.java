package com.Kashish.secure_order_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest
{
	@NotBlank(message="Reset Token cannot be empty")
	private String token;
	
	@NotBlank(message="Enter new password")
	@Size(min=6,message="Password must be atleast of 6 characters")
	private String password;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
