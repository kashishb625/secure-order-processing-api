package com.Kashish.secure_order_api.dto;

import java.time.LocalDateTime;

public class OrderResponse
{
	private Long id;
	private double totalAmount;
	private String status;
	private LocalDateTime orderDate;

	private Long customerId;
	private String customerName;
	private String username;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public double getTotalAmount() 
	{
		return totalAmount;
	}
	
	public void setTotalAmount(double totalAmount) 
	{
		this.totalAmount = totalAmount;
	}
	
	public String getStatus() 
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public LocalDateTime getOrderDate()
	{
		return orderDate;
	}
	
	public void setOrderDate(LocalDateTime orderDate) 
	{
		this.orderDate = orderDate;
	}
	
	public Long getCustomerId() 
	{
		return customerId;
	}
	
	public void setCustomerId(Long customerId) 
	{
		this.customerId = customerId;
	}
	
	public String getCustomerName() 
	{
		return customerName;
	}
	
	public void setCustomerName(String customerName) 
	{
		this.customerName = customerName;
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	
	

}
