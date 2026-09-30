package com.Kashish.secure_order_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.mockito.Mock;

import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.Kashish.secure_order_api.audit.AuditLogService;
import com.Kashish.secure_order_api.entity.Product;
import com.Kashish.secure_order_api.exception.ResourceNotFoundException;
import com.Kashish.secure_order_api.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest 
{
	@Mock
	private ProductRepository productRepository;

	@Mock
	private AuditLogService auditLogService;
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private Authentication authentication;
	
	@Mock
	private SecurityContext securityContext;
	
	@BeforeEach
	void setUp()
	{
	    
	    lenient().when(securityContext.getAuthentication()).thenReturn(authentication);

	   lenient().when(authentication.getName()).thenReturn("testuser");

	    SecurityContextHolder.setContext(securityContext);
	}
	
	
	@Test
	void createProduct_shouldSaveAndReturnProduct()
	{
		Product product=new Product();
		
		product.setP_name("Samsung Galaxy F23");
		product.setP_description("Smart Phone");
		product.setPrice(20000);
		product.setStock_Quantity(10);
		
		when(productRepository.save(any(Product.class))).thenReturn(product);
		
		Product result=productService.createProduct(product);
		assertNotNull(result);
		assertEquals("Samsung Galaxy F23",result.getP_name());
		verify(productRepository).save(product);
	}
	
	
	@Test
	void updateProduct_shouldUpdateAndReturnProduct()
	{
		Product existingProduct=new Product();
		
		existingProduct.setP_name("Old Product");
		existingProduct.setP_description("Old Description");
		existingProduct.setPrice(10000);
		existingProduct.setStock_Quantity(5);
		
		Product updatedProduct = new Product();
		updatedProduct.setP_name("Updated Product");
		updatedProduct.setP_description("Updated Description");
		updatedProduct.setPrice(15000);
		updatedProduct.setStock_Quantity(10);
		
		Long id = 1L;
		when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(existingProduct)).thenReturn(existingProduct);
		
		Product result = productService.updateProduct(id, updatedProduct);
		assertNotNull(result);
		assertEquals("Updated Product", result.getP_name());
		assertEquals("Updated Description", result.getP_description());
		assertEquals(15000, result.getPrice());
		assertEquals(10, result.getStock_Quantity());
		
		verify(productRepository).save(existingProduct);
		
	}
	
	@Test
	void deleteProduct_shouldDeleteProduct()
	{
	    Product product = new Product();

	    product.setP_name("Test Product");
	    product.setPrice(10000);
	    product.setStock_Quantity(5);

	    Long id = 1L;

	    when(productRepository.findById(id)).thenReturn(Optional.of(product));

	    productService.deleteProduct(id);

	    verify(productRepository).delete(product);
	}
	
	@Test
	void deleteProduct_shouldThrowExceptionWhenProductNotFound()
	{
	    Long id = 999L;

	    when(productRepository.findById(id)).thenReturn(Optional.empty());

	    assertThrows(ResourceNotFoundException.class, () -> {
	        productService.deleteProduct(id);
	    });
	}

}
