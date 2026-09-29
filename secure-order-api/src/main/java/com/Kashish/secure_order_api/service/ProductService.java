package com.Kashish.secure_order_api.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Kashish.secure_order_api.audit.AuditLogService;
import com.Kashish.secure_order_api.entity.Product;
import com.Kashish.secure_order_api.exception.ResourceNotFoundException;
import com.Kashish.secure_order_api.repository.ProductRepository;

@Service
public class ProductService 
{

	private final ProductRepository productRepository;
	private final AuditLogService auditLogService;

	public ProductService(ProductRepository productRepository,AuditLogService auditLogService) 
	{
			this.productRepository = productRepository;
			this.auditLogService=auditLogService;
	}
	
	public Product createProduct(Product product)
	{
	    Product savedProduct = productRepository.save(product);
	    auditLogService.log(getUsername(), "CREATE_PRODUCT");
	    return savedProduct;
	}
	
	public List<Product>getAllProducts()
	{
		return productRepository.findAll();
	}
	
	public Page<Product> getProducts(Pageable pageable)
	{
		return productRepository.findAll(pageable);
	}
	
	public List<Product> searchProductByName(String name)
	{
		return productRepository.searchByName(name);	
	}
	
	public List<Product> searchProductsByNameAndPrice(String name, Double minPrice, Double maxPrice)
	{
		return productRepository.searchByNameAndPrice(name, minPrice, maxPrice);
	}
	
	public List<Product> searchProductByPrice(Double minPrice,Double maxPrice)
	{
		return productRepository.findByPriceRange(minPrice, maxPrice);
	}
	
	public List<Product> searchProductByStockRange(Integer minStock,Integer maxStock)
	{
		return productRepository.findByStockRange(minStock, maxStock);
	}
	
	public List<Product>searchProductByNamePriceAndStock(String name, Double minPrice, Double maxPrice,Integer minStock,Integer maxStock)
	{
		return productRepository.searchByNamePriceAndStock(name,minPrice,maxPrice,minStock,maxStock);
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
		
		Product savedProduct= productRepository.save(existingProduct);
		auditLogService.log(getUsername(), "UPDATE_PRODUCT");
		return savedProduct;
		
	}
	
	public void deleteProduct(Long id)
	{
		Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found with id: "+id));
		productRepository.delete(product);
		auditLogService.log(getUsername(), "DELETE_PRODUCT");
	}
	
	public String getUsername()
	{
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
