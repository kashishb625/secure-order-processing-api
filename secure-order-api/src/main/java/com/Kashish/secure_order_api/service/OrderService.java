package com.Kashish.secure_order_api.service;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Kashish.secure_order_api.audit.AuditLogService;
import com.Kashish.secure_order_api.entity.Customer;
import com.Kashish.secure_order_api.entity.Order;
import com.Kashish.secure_order_api.exception.ResourceNotFoundException;
import com.Kashish.secure_order_api.repository.CustomerRepository;
import com.Kashish.secure_order_api.repository.OrderRepository;

@Service
public class OrderService
{

	private OrderRepository orderRepository;
	private CustomerRepository customerRepository;
	private final AuditLogService auditLogService;

	public OrderService(OrderRepository orderRepository,CustomerRepository customerRepository,AuditLogService auditLogService)
	{
		this.customerRepository= customerRepository;
		this.orderRepository = orderRepository;
		this.auditLogService=auditLogService;
	}
	
	public Order createOrder(Order order)
	{
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin=authentication.getAuthorities()
				.stream().anyMatch(authority->
				authority.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin) {
			String username=authentication.getName();
			Customer customer=customerRepository.findByUserUsername(username)
					.orElseThrow(()->new ResourceNotFoundException("Customer profile not found for user: "+username));
			
			order.setCustomer(customer);
		}
		
		order.setOrderDate(LocalDateTime.now());
		Order savedOrder= orderRepository.save(order);
		auditLogService.log(authentication.getName(),"CREATE_ORDER");
		return savedOrder;
	}
	
	public List<Order> getAllOrders()
	{
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		
		String username=authentication.getName();
		boolean isAdmin=authentication.getAuthorities()
				.stream().anyMatch(authority->
				authority.getAuthority().equals("ROLE_ADMIN"));
		
		if(isAdmin)
		{
			return orderRepository.findAll();
		}
		return orderRepository.findByCustomerUserUsername(username);
		
	}
	
	public Order getOrderById(Long id)
	{
	    Order order = orderRepository.findById(id)
	            .orElseThrow(() ->
	                new ResourceNotFoundException(
	                    "Order not found with the order id: " + id));

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    boolean isAdmin = authentication.getAuthorities()
	            .stream()
	            .anyMatch(authority ->
	                    authority.getAuthority().equals("ROLE_ADMIN"));

	    if (isAdmin)
	    {
	        return order;
	    }

	    String username = authentication.getName();

	    if (order.getCustomer() == null ||
	            order.getCustomer().getUser() == null)
	    {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN,
	                "You are not allowed to access this order");
	    }

	    String orderUsername =
	            order.getCustomer().getUser().getUsername();

	    if (!orderUsername.equals(username))
	    {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN,
	                "You are not allowed to access this order");
	    }

	    return order;
	}
	
	
	public Order updateOrder(Long id,Order updatedOrder)
	{
		Order existingOrder=orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found with id: "+id));;
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		
		boolean isAdmin=authentication.getAuthorities().stream()
				.anyMatch(authority->authority.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin)
		{
			String username=authentication.getName();
			
			if(existingOrder.getCustomer()==null || existingOrder.getCustomer().getUser()==null)
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to update this order");
			}
			
			String orderUsername=existingOrder.getCustomer().getUser().getUsername();
			
			if(!orderUsername.equals(username))
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to update this order");
			}
		}
		if(isAdmin)
		{
		existingOrder.setCustomer(updatedOrder.getCustomer());
		}
		existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
		existingOrder.setStatus(updatedOrder.getStatus());
		
		Order savedOrder=orderRepository.save(existingOrder);
		auditLogService.log(authentication.getName(), "UPDATE_ORDER");
		return savedOrder;
	}
	
	
	public void deleteOrder(Long id)
	{
		Order order=orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found with id: "+id));
		
		Authentication authentication =
		        SecurityContextHolder.getContext().getAuthentication();

		boolean isAdmin = authentication.getAuthorities()
		        .stream()
		        .anyMatch(authority ->
		                authority.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin)
		{
			String username=authentication.getName();
			if(order.getCustomer()==null || order.getCustomer().getUser()==null)
				
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to delete this order");
			}
			
			String orderUsername=order.getCustomer().getUser().getUsername();
			
			if(!orderUsername.equals(username))
			{
				throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to delete this order");
			}
		}
		
		orderRepository.delete(order);
		auditLogService.log(authentication.getName(), "DELETE_ORDER");
		
	}
	
}
