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
	public Customer createCustomer(@Valid @RequestBody Customer customer)
	{
		return customerService.createCustomer(customer);
	}
	
	@GetMapping
	public List<Customer> getAllCustomers()
	{
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/{id}")
	public Customer getById(@PathVariable Long id)
	{
		return customerService.getById(id);
	}
	
	@PutMapping("/{id}")
	public Customer updateCustomer(@PathVariable Long id,@RequestBody Customer customer)
	{
		return customerService.updateCustomer(id, customer);
	}
	
	@DeleteMapping("/{id}")
	public String deleteCustomer(@PathVariable Long id)
	{
		customerService.deleteCustomer(id);
		return "Customer Deleted Successfully!!"; 
	}

}
