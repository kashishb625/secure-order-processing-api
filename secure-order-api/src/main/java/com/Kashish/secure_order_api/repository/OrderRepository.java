package com.Kashish.secure_order_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kashish.secure_order_api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>
{
	List<Order> findByCustomerUserUsername(String username);

}
