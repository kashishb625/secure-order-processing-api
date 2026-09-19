package com.Kashish.secure_order_api.controller;

import java.util.List; 

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Kashish.secure_order_api.entity.User;
import com.Kashish.secure_order_api.service.UserService;
import com.Kashish.secure_order_api.dto.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController
{
	private final UserService userService;

	public UserController(UserService userService) 
	{
		this.userService = userService;
	}
	
	@PostMapping
	public UserResponse createUser(@Valid @RequestBody User user)
	{
		User createdUser=userService.createUser(user);
		return convertToResponse(createdUser);
	}
	
	private UserResponse convertToResponse(User user)
	{
		UserResponse response=new UserResponse();
		response.setId(user.getId());
		response.setUsername(user.getUsername());
		response.setRole(user.getRole());
		
		return response;
	}
	
	@GetMapping
	public List<UserResponse> getAllUsers()
	{
		return userService.getAllUsers().stream().map(this::convertToResponse).toList();		
	}
	
	@GetMapping("/{id}")
	public UserResponse getUserById(@PathVariable Long id)
	{
		User user=userService.getUserById(id);
		return convertToResponse(user);
	}
	
	
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id)
	{
		userService.deleteUser(id);
		return "User Deleted Successfully!!";
	}
}
