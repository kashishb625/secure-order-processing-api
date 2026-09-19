package com.Kashish.secure_order_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kashish.secure_order_api.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	User findByUsername(String username);

}
