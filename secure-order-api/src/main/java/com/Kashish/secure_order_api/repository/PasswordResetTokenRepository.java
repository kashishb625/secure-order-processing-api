package com.Kashish.secure_order_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kashish.secure_order_api.entity.PasswordResetToken;
import com.Kashish.secure_order_api.entity.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>
{

	PasswordResetToken findByToken(String token);
	PasswordResetToken findByUser(User user);
}
