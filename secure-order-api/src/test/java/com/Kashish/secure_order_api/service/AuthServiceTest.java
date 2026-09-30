package com.Kashish.secure_order_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Kashish.secure_order_api.audit.AuditLogService;
import com.Kashish.secure_order_api.dto.LoginRequest;
import com.Kashish.secure_order_api.dto.LoginResponse;
import com.Kashish.secure_order_api.dto.UserResponse;
import com.Kashish.secure_order_api.entity.User;
import com.Kashish.secure_order_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest 
{
	@InjectMocks
	private AuthService authService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	 @Mock
	 private AuditLogService auditLogService;
	 
	 @Mock
	 private JwtService jwtService;
	 
	 
	 @Test
	 void login_shouldReturnTokenForValidCredentials()
	 {
		 LoginRequest request=new LoginRequest();
		 request.setUsername("TestUser");
		 request.setPassword("TestUser123");
		 
		 User user=new User();
		 user.setUsername("TestUser");
		 user.setPassword("encodedPassword");
		 
		 when(userRepository.findByUsername("TestUser")).thenReturn(user);
		 when(passwordEncoder.matches("TestUser123","encodedPassword" )).thenReturn(true);
		 when(jwtService.generateToken("TestUser")).thenReturn("test-jwt-token");
		 
		 LoginResponse result=authService.login(request);
		 
		 assertNotNull(result);
		 assertEquals("test-jwt-token", result.getToken());
		 
		 verify(userRepository).findByUsername("TestUser");
		    verify(passwordEncoder).matches("TestUser123", "encodedPassword");
		    verify(jwtService).generateToken("TestUser");
		    verify(auditLogService).log("TestUser", "LOGIN");
		 
	 }

}
