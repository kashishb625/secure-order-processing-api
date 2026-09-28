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

import com.Kashish.secure_order_api.dto.ApiResponse;
import com.Kashish.secure_order_api.dto.OrderResponse;
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
	public ApiResponse createOrder(@Valid @RequestBody Order order)
	{
		Order createdOrder= orderService.createOrder(order);
		OrderResponse response=convertToResponse(createdOrder);
		return new ApiResponse(201,"Order created Successfully!!",response);
	}
	
	public OrderResponse convertToResponse(Order order)
	{
		OrderResponse response=new OrderResponse();
		
		response.setId(order.getId());
		response.setTotalAmount(order.getTotalAmount());
		response.setStatus(order.getStatus());
		response.setOrderDate(order.getOrderDate());
		
		if(order.getCustomer()!=null)
		{
			response.setCustomerName(order.getCustomer().getName());
			response.setCustomerId(order.getCustomer().getId());
			
			if(order.getCustomer().getUser()!=null)
			{
				response.setUsername(order.getCustomer().getUser().getUsername());
			}
		}
		return response;
	}
	
	
	@GetMapping
	public ApiResponse getAllOrders()
	{
		List<OrderResponse> responses= orderService.getAllOrders().stream()
				.map(this::convertToResponse).toList();
		
		return new ApiResponse(200,"Orders fetched successfully!!",responses);
	}
	
	@GetMapping("/{id}")
	public ApiResponse getOrderbyId(@PathVariable Long id)
	{
		Order order= orderService.getOrderById(id);
		OrderResponse response=convertToResponse(order);
		return new ApiResponse(200,"Order Fetched successfully!!",response);
	}
	
	@PutMapping("/{id}")
	public ApiResponse updateOrder(@PathVariable Long id,@Valid @RequestBody Order order)
	{
		Order updatedOrder=orderService.updateOrder(id, order);
		OrderResponse response= convertToResponse(updatedOrder);
		return new ApiResponse(200,"Order details updated successfully!!",response);
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse deleteOrder(@PathVariable Long id)
	{
		orderService.deleteOrder(id);
		return new ApiResponse(200, "Order Deleted Successfully!!",null);
	}
	
}
