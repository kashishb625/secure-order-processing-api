package com.Kashish.secure_order_api.service;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Kashish.secure_order_api.entity.PasswordResetToken;
import com.Kashish.secure_order_api.entity.User;
import com.Kashish.secure_order_api.exception.PasswordResetException;
import com.Kashish.secure_order_api.exception.ResourceNotFoundException;
import com.Kashish.secure_order_api.repository.PasswordResetTokenRepository;
import com.Kashish.secure_order_api.repository.UserRepository;

@Service
public class PasswordResetService 
{
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) 
	{		
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.userRepository = userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	public String generateResetToken(String username)
	{
		User user=userRepository.findByUsername(username);
		
		if(user==null)
		{
			throw new ResourceNotFoundException("User does not exist");
		}
		
		PasswordResetToken existingToken=passwordResetTokenRepository.findByUser(user);
		
		if(existingToken!=null)
		{
			passwordResetTokenRepository.delete(existingToken);
		}
		
		String token=UUID.randomUUID().toString();
		
		PasswordResetToken resetToken=new PasswordResetToken();
		resetToken.setToken(token);
		resetToken.setExpiry(LocalDateTime.now().plusMinutes(15));
		resetToken.setUser(user);
		
		passwordResetTokenRepository.save(resetToken);
		return token;
	}
	
	public void resetPassword(String token,String newPassword)
	{
		PasswordResetToken resetToken=passwordResetTokenRepository.findByToken(token);
		
		if(resetToken==null)
		{
			throw new PasswordResetException("Invalid reset token");
		}
		
		if(resetToken.getExpiry().isBefore(LocalDateTime.now()))
		{
			passwordResetTokenRepository.delete(resetToken);
			throw new PasswordResetException("Reset Token has expired");
		}
		
		User user=resetToken.getUser();
		
		String encodedPassword=passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		
		userRepository.save(user);
		
		passwordResetTokenRepository.delete(resetToken);
	}
	
	

}
