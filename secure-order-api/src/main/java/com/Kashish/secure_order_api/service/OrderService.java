package com.Kashish.secure_order_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Kashish.secure_order_api.entity.Order;
import com.Kashish.secure_order_api.repository.OrderRepository;

@Service
public class OrderService
{

	private OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
				
		this.orderRepository = orderRepository;
	}
	
	public Order createOrder(Order order)
	{
		order.setOrderDate(LocalDateTime.now());
		return orderRepository.save(order);
	}
	
	public List<Order> getAllOrders()
	{
		return orderRepository.findAll();
	}
	
	public Order getOrderById(Long id)
	{
		return orderRepository.findById(id).orElse(null);
	}
	
	public Order updateOrder(Long id,Order updatedOrder)
	{
		Order existingOrder=orderRepository.findById(id).orElse(null);
		if(existingOrder==null)
		{
			return null;
		}
		
		existingOrder.setCustomer(updatedOrder.getCustomer());
		existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
		existingOrder.setStatus(updatedOrder.getStatus());
		
		return orderRepository.save(existingOrder);
	}
	
	public void deleteOrder(Long id)
	{
		orderRepository.deleteById(id);
		
	}
	
}
