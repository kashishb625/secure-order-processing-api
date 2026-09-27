package com.Kashish.secure_order_api.controller;


import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

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
	public Page<Product> getAllProducts(Pageable pageable)
	{
		return productService.getProducts(pageable);
	}
	
	@GetMapping("/search")
	public List<Product> searchProducts(@RequestParam String name)
	{
		return productService.searchProductByName(name);
	}
	
	@GetMapping("/search/price")
	public List<Product> searchProductByPrice(@RequestParam("minPrice") Double minPrice,
			@RequestParam("maxPrice") Double maxprice)
	{
		return productService.searchProductByPrice(minPrice, maxprice);
	}
	
	@GetMapping("/search/filter")
	public List<Product> searchProductByNameAndPrice(@RequestParam("name") String name,
			@RequestParam("minPrice") Double minPrice, @RequestParam("maxPrice")Double maxPrice )
	{
		return productService.searchProductsByNameAndPrice(name, minPrice, maxPrice);
	}
	
	@GetMapping("/search/stock")
	public List<Product> findbyStockRange(@RequestParam("minStock") Integer minStock,@RequestParam("maxStock") Integer maxStock)
	{
		return productService.searchProductByStockRange(minStock, maxStock);
	}
	
	@GetMapping("/search/filter/all")
	public List<Product> searchProductByNamePriceAndStock(@RequestParam("name") String name,
		        @RequestParam("minPrice") Double minPrice,
		        @RequestParam("maxPrice") Double maxPrice,
		        @RequestParam("minStock")Integer minStock,
		        @RequestParam("maxStock")Integer maxStock)
	{
		return productService.searchProductByNamePriceAndStock(name, minPrice, maxPrice, minStock, maxStock);
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
