package com.Kashish.secure_order_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kashish.secure_order_api.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> 
{
	 Optional<Customer> findByUserUsername(String username);

}
