package com.Kashish.secure_order_api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Kashish.secure_order_api.dto.LoginRequest;
import com.Kashish.secure_order_api.dto.LoginResponse;
import com.Kashish.secure_order_api.entity.User;
import com.Kashish.secure_order_api.exception.InvalidCredentialsException;
import com.Kashish.secure_order_api.repository.UserRepository;

@Service
public class AuthService 
{
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtService jwtService) 
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService=jwtService;
	}
	
	public LoginResponse login(LoginRequest request)
	{
		User user=userRepository.findByUsername(request.getUsername());
		
		if(user==null)
		{
			throw new InvalidCredentialsException("Invalid username or password");
		}
		if(!passwordEncoder.matches(request.getPassword(),user.getPassword()))
		{
			throw new InvalidCredentialsException("Invalid username or password");
		}
		
		String token=jwtService.generateToken(user.getUsername());
		
		return new LoginResponse(token);
	}
	
	
	
	

}
