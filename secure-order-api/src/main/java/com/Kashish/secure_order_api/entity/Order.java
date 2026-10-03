package com.Kashish.secure_order_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="orders")
public class Order 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="customer_id",nullable=false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;

	@Min(value=1, message="Quantity must be at least 1.")
	private int quantity;
	
	@Min(value=0,message="Total Amount cannot be negative.")
	private double totalAmount;
	@NotBlank(message="Order status is required.")
	private String status;
	private LocalDateTime orderDate;
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getTotalAmount() {
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

	
	

}
