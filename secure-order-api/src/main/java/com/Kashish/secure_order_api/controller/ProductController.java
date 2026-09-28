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

import com.Kashish.secure_order_api.dto.ApiResponse;
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
	public ApiResponse createProduct(@Valid @RequestBody Product product)
	{
		Product savedproduct=productService.createProduct(product);
		return new ApiResponse(200,"Product created successfully!!", savedproduct);
	}
	
	@GetMapping
	public ApiResponse getAllProducts(Pageable pageable)
	{
		Page<Product> products=productService.getProducts(pageable);
		return new ApiResponse(200,"Products Fetched Successfully!!",products);
	}
	
	@GetMapping("/search")
	public ApiResponse searchProducts(@RequestParam String name)
	{
		List<Product> products=productService.searchProductByName(name);
		return new ApiResponse(200,"Products found successfully!!",products);
	}
	
	@GetMapping("/search/price")
	public ApiResponse searchProductByPrice(@RequestParam("minPrice") Double minPrice,
			@RequestParam("maxPrice") Double maxprice)
	{
		List<Product> products= productService.searchProductByPrice(minPrice, maxprice);
		return new ApiResponse(200,"Products found successfully!!",products);
	}
	
	@GetMapping("/search/filter")
	public ApiResponse searchProductByNameAndPrice(@RequestParam("name") String name,
			@RequestParam("minPrice") Double minPrice, @RequestParam("maxPrice")Double maxPrice )
	{
		List<Product> products=productService.searchProductsByNameAndPrice(name, minPrice, maxPrice);
		return new ApiResponse(200,"Products found successfully!!",products);
	}
	
	@GetMapping("/search/stock")
	public ApiResponse findbyStockRange(@RequestParam("minStock") Integer minStock,@RequestParam("maxStock") Integer maxStock)
	{
		List<Product> products= productService.searchProductByStockRange(minStock, maxStock);
		return new ApiResponse(200,"Products found successfully!!",products);
	}
	
	@GetMapping("/search/filter/all")
	public ApiResponse searchProductByNamePriceAndStock(@RequestParam("name") String name,
		        @RequestParam("minPrice") Double minPrice,
		        @RequestParam("maxPrice") Double maxPrice,
		        @RequestParam("minStock")Integer minStock,
		        @RequestParam("maxStock")Integer maxStock)
	{
		List<Product> products= productService.searchProductByNamePriceAndStock(name, minPrice, maxPrice, minStock, maxStock);
		return new ApiResponse(200,"Products found successfully!!",products);
	}
	
	@GetMapping("/{id}")
	public ApiResponse getProductById(@PathVariable Long id)
	{
		Product product=productService.getProductById(id);
		return new ApiResponse(200, "Product fetched successfully!!", product);
	}
	
	@PutMapping("/{id}")
	public ApiResponse updateProduct(@PathVariable Long id,@Valid @RequestBody Product product)
	{
		Product updatedProduct=productService.updateProduct(id, product);
		return new ApiResponse(200, "Product details updated successfully!!", updatedProduct);
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse deleteById(@PathVariable Long id)
	{
		productService.deleteProduct(id);
		return new ApiResponse(200,"Product deleted successfully",null);
	}

}
