package com.Kashish.secure_order_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Kashish.secure_order_api.entity.Customer;
import com.Kashish.secure_order_api.entity.User;
import com.Kashish.secure_order_api.exception.ResourceNotFoundException;
import com.Kashish.secure_order_api.repository.CustomerRepository;

@Service
public class CustomerService 
{
	private final CustomerRepository customerRepository;
	private final UserService userService;

	public CustomerService(CustomerRepository customerRepository,UserService userService) 
	{
		this.customerRepository = customerRepository;
		this.userService=userService;
	}
	
	public Customer createCustomer(Customer customer)
	{
		String username=SecurityContextHolder.getContext()
				.getAuthentication().getName();
		User user=userService.getUserByUsername(username);
		customer.setUser(user);
		
		return customerRepository.save(customer);
	}
	
	public List<Customer> getAllCustomers()
	{
		return customerRepository.findAll();
	}
	
	public Customer getById(Long id)
	{
		Customer customer=customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer not found with id: "+id));
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin=authentication.getAuthorities()
				.stream().anyMatch(authority->
				authority.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin)
		{
			String username=authentication.getName();
			
			if(customer.getUser()==null)
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to access this customer");
			}
			
			String customerUsername=customer.getUser().getUsername();
			
			if(!customerUsername.equals(username))
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to access this customer");
			}
		}
		
		return customer;
	}
	
	public Customer updateCustomer(Long id,Customer updatedCustomer)
	{
		Customer existingCustomer=customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer not found with id: "+id));
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin=authentication.getAuthorities()
				.stream().anyMatch(authority->
				authority.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin)
		{
			String username=authentication.getName();
			
			if(existingCustomer.getUser()==null)
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to update this customer");
			}
			
			String customerUsername=existingCustomer.getUser().getUsername();
			
			if(!customerUsername.equals(username))
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to update this customer");
			}
		}
		
		existingCustomer.setName(updatedCustomer.getName());
		existingCustomer.setEmail(updatedCustomer.getEmail());
		existingCustomer.setPhone(updatedCustomer.getPhone());
		
		return customerRepository.save(existingCustomer);
	}
	
	public void deleteCustomer(Long id)
	{
		Customer customer=customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer not found with id: "+id));
		

		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin=authentication.getAuthorities()
				.stream().anyMatch(authority->
				authority.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin)
		{
			String username=authentication.getName();
			
			if(customer.getUser()==null)
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to delete this customer");
			}
			
			String customerUsername=customer.getUser().getUsername();
			
			if(!customerUsername.equals(username))
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to deletethis customer");
			}
		}
		
		customerRepository.delete(customer);
	}

}
