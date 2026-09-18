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

import com.Kashish.secure_order_api.entity.Order;
import com.Kashish.secure_order_api.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController 
{

	private OrderService orderService;

	public OrderController(OrderService orderService) 
	{
		this.orderService = orderService;
	}
	
	@PostMapping
	public Order createOrder(@Valid @RequestBody Order order)
	{
		return orderService.createOrder(order);
	}
	
	@GetMapping
	public List<Order> getAllOrders()
	{
		return orderService.getAllOrders();
	}
	
	@GetMapping("/{id}")
	public Order getOrderbyId(@PathVariable Long id)
	{
		return orderService.getOrderById(id);
	}
	
	@PutMapping("/{id}")
	public Order updateOrder(@PathVariable Long id,@RequestBody Order order)
	{
		return orderService.updateOrder(id, order);
	}
	
	@DeleteMapping("/{id}")
	public String deleteOrder(@PathVariable Long id)
	{
		orderService.deleteOrder(id);
		return "Order Deleted Successfully!!";
	}
	
}
