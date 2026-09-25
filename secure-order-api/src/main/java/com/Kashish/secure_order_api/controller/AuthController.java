package com.Kashish.secure_order_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Kashish.secure_order_api.dto.LoginRequest;
import com.Kashish.secure_order_api.dto.LoginResponse;
import com.Kashish.secure_order_api.dto.ResetPasswordRequest;
import com.Kashish.secure_order_api.service.AuthService;
import com.Kashish.secure_order_api.service.PasswordResetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
	private AuthService authService;
	private PasswordResetService passwordResetService;

	public AuthController(AuthService authService,PasswordResetService passwordResetService) 
	{
		this.authService = authService;
		this.passwordResetService=passwordResetService;
	}
	
	@PostMapping("/login")
	public LoginResponse login (@Valid @RequestBody LoginRequest request) 
	{
		return authService.login(request);
	}
	
	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestBody String username)
	{
		return passwordResetService.generateResetToken(username);
	}
	
	@PostMapping("/reset-password")
	public String resetPassword(@Valid @RequestBody ResetPasswordRequest request)
	{
		passwordResetService.resetPassword(request.getToken(),request.getPassword());
		
		return "Password reset successfully";
	}
	

}
