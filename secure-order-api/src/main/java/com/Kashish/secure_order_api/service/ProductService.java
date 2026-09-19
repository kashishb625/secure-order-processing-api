package com.Kashish.secure_order_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Kashish.secure_order_api.entity.Product;
import com.Kashish.secure_order_api.exception.ResourceNotFoundException;
import com.Kashish.secure_order_api.repository.ProductRepository;

@Service
public class ProductService 
{

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) 
	{
			this.productRepository = productRepository;
	}
	
	public Product createProduct(Product product)
	{
		return productRepository.save(product);
		
	}
	
	public List<Product>getAllProducts()
	{
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id)
	{
		return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found with id: "+id));
	}
	
	public Product updateProduct(Long id,Product updatedProduct)
	{
		Product existingProduct= productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found with id: "+id));
		
		existingProduct.setP_name(updatedProduct.getP_name());
		existingProduct.setP_description(updatedProduct.getP_description());
		existingProduct.setPrice(updatedProduct.getPrice());
		existingProduct.setStock_Quantity(updatedProduct.getStock_Quantity());
		
		return productRepository.save(existingProduct);
	}
	
	public void deleteProduct(Long id)
	{
		Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found with id: "+id));
		productRepository.delete(product);
	}
}
