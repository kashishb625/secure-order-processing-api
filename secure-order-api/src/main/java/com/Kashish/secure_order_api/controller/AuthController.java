package com.Kashish.secure_order_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Kashish.secure_order_api.dto.LoginRequest;
import com.Kashish.secure_order_api.dto.LoginResponse;
import com.Kashish.secure_order_api.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
	private AuthService authService;

	public AuthController(AuthService authService) 
	{
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public LoginResponse login (@Valid @RequestBody LoginRequest request) 
	{
		return authService.login(request);
	}
	

}
