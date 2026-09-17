package com.Kashish.secure_order_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kashish.secure_order_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>
{

}
