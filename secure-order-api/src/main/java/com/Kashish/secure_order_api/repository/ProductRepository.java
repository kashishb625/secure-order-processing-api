package com.Kashish.secure_order_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Kashish.secure_order_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>
{
	@Query("SELECT p FROM Product p WHERE LOWER(p.p_name) LIKE LOWER(CONCAT('%',:name,'%'))")
	List<Product> searchByName(@Param("name") String name);

	@Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
	List<Product> findByPriceRange(@Param("minPrice") Double minPrice,
	                               @Param("maxPrice") Double maxPrice);
	
	@Query("SELECT p FROM Product p WHERE " +
		       "LOWER(p.p_name) LIKE LOWER(CONCAT('%', :name, '%')) " +
		       "AND p.price BETWEEN :minPrice AND :maxPrice")
		List<Product> searchByNameAndPrice(
		        @Param("name") String name,
		        @Param("minPrice") Double minPrice,
		        @Param("maxPrice") Double maxPrice);
	
	@Query("SELECT p FROM Product p WHERE p.stock_Quantity BETWEEN :minStock AND :maxStock")
	List<Product> findByStockRange(@Param("minStock") Integer minStock,@Param("maxStock") Integer maxStock);
	
	@Query("SELECT p FROM Product p WHERE " +
		       "LOWER(p.p_name) LIKE LOWER(CONCAT('%', :name, '%')) " +
		       "AND p.price BETWEEN :minPrice AND :maxPrice " +
		       "AND p.stock_Quantity BETWEEN :minStock AND :maxStock")
	List<Product> searchByNamePriceAndStock(
		        @Param("name") String name,
		        @Param("minPrice") Double minPrice,
		        @Param("maxPrice") Double maxPrice,
		        @Param("minStock")Integer minStock,
		        @Param("maxStock")Integer maxStock);
}
