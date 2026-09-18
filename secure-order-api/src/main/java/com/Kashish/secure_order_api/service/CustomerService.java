package com.Kashish.secure_order_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Kashish.secure_order_api.entity.Customer;
import com.Kashish.secure_order_api.exception.ResourceNotFoundException;
import com.Kashish.secure_order_api.repository.CustomerRepository;

@Service
public class CustomerService 
{
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) 
	{
		this.customerRepository = customerRepository;
	}
	
	public Customer createCustomer(Customer customer)
	{
		return customerRepository.save(customer);
	}
	
	public List<Customer> getAllCustomers()
	{
		return customerRepository.findAll();
	}
	
	public Customer getById(Long id)
	{
		return customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer not found with id: "+id));
	}
	
	public Customer updateCustomer(Long id,Customer updatedCustomer)
	{
		Customer existingCustomer=customerRepository.findById(id).orElse(null);
		
		if(existingCustomer==null)
		{
			return null;
		}
		
		existingCustomer.setName(updatedCustomer.getName());
		existingCustomer.setEmail(updatedCustomer.getEmail());
		existingCustomer.setPhone(updatedCustomer.getPhone());
		
		return customerRepository.save(existingCustomer);
	}
	
	public void deleteCustomer(Long id)
	{
		customerRepository.deleteById(id);
	}

}
