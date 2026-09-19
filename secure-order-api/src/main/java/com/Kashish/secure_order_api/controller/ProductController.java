package com.Kashish.secure_order_api.controller;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Kashish.secure_order_api.entity.Product;
import com.Kashish.secure_order_api.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductController 
{
	private final ProductService productService;

	public ProductController(ProductService productService)
	{
		this.productService = productService;
	}
	
	@PostMapping
	public Product createProduct(@Valid @RequestBody Product product)
	{
		return productService.createProduct(product);
		
	}
	
	@GetMapping
	public List<Product> getAllProducts()
	{
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id)
	{
		return productService.getProductById(id);
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id,@Valid @RequestBody Product product)
	{
		return productService.updateProduct(id, product);
	}
	
	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable Long id)
	{
		productService.deleteProduct(id);
		return "Product deleted successfully";
	}

}
