package com.Kashish.secure_order_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Kashish.secure_order_api.dto.CustomerResponse;
import com.Kashish.secure_order_api.entity.Customer;
import com.Kashish.secure_order_api.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController 
{
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) 
	{		
		this.customerService = customerService;
	}
	
	@PostMapping
	public CustomerResponse createCustomer(@Valid @RequestBody Customer customer)
	{
		Customer createdCustomer=customerService.createCustomer(customer);
		return convertToResponse(createdCustomer);
	}
	
	private CustomerResponse convertToResponse(Customer customer)
	{
		CustomerResponse response=new CustomerResponse();
		
		response.setId(customer.getId());
		response.setName(customer.getName());
		response.setEmail(customer.getEmail());
		response.setPhone(customer.getPhone());
		
		if(customer.getUser()!=null)
		{
			response.setUserId(customer.getUser().getId());
			response.setUsername(customer.getUser().getUsername());
		}
		return response;
	}
	
	
	@GetMapping
	public List<CustomerResponse> getAllCustomers()
	{
		return customerService.getAllCustomers()
				.stream()
				.map(customer -> convertToResponse(customer)).toList();
	}
	
	@GetMapping("/{id}")
	public CustomerResponse getById(@PathVariable Long id)
	{
		Customer customer=customerService.getById(id);
		return convertToResponse(customer);
	}
	
	@PutMapping("/{id}")
	public CustomerResponse updateCustomer(@PathVariable Long id,@Valid @RequestBody Customer customer)
	{
		Customer updatedCustomer=customerService.updateCustomer(id, customer);
		return convertToResponse(updatedCustomer);
	}
	
	@DeleteMapping("/{id}")
	public String deleteCustomer(@PathVariable Long id)
	{
		customerService.deleteCustomer(id);
		return "Customer Deleted Successfully!!"; 
	}

}
