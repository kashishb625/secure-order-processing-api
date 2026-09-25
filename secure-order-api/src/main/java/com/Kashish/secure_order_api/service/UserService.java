package com.Kashish.secure_order_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Kashish.secure_order_api.dto.UserUpdateRequest;
import com.Kashish.secure_order_api.entity.User;
import com.Kashish.secure_order_api.exception.ResourceNotFoundException;
import com.Kashish.secure_order_api.repository.UserRepository;

@Service
public class UserService
{
	private final UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) 
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User createUser(User user)
	{
		String encodedPassword=passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}
	
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	public User getUserById(Long id)
	{
		return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+id));
	}
	
	public User getUserByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}
	
	public User updateUser(Long id,UserUpdateRequest request)
	{
		User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: "+id));
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		
		boolean isAdmin=authentication.getAuthorities().stream()
				.anyMatch(authority->authority.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin)
		{
			String username=authentication.getName();
			
			if(!user.getUsername().equals(username))
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to update this user");
			}
			
		}
		
		
		String encodedPassword=passwordEncoder.encode(request.getPassword());
		user.setPassword(encodedPassword);
		
		return userRepository.save(user);
	}
	
	public void deleteUser(Long id)
	{
		User user= userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+id));
		
		userRepository.delete(user);
	}
	

}
