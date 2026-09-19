package com.Kashish.secure_order_api.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	public void deleteUser(Long id)
	{
		User user= userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+id));
		
		userRepository.delete(user);
	}
	

}
