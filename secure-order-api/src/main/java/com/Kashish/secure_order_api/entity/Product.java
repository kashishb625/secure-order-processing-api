package com.Kashish.secure_order_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product 
{
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	private String p_name;
	private String p_description;
	private double price;
	private int stock_Quantity;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getP_name() {
		return p_name;
	}
	
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	
	public String getP_description() {
		return p_description;
	}
	public void setP_description(String p_description) {
		this.p_description = p_description;
	}
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getStock_Quantity() {
		return stock_Quantity;
	}
	
	public void setStock_Quantity(int stock_Quantity) {
		this.stock_Quantity = stock_Quantity;
	}
	
	
	
	

}
