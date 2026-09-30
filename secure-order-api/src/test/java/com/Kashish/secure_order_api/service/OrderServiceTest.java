package com.Kashish.secure_order_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.Kashish.secure_order_api.audit.AuditLogService;
import com.Kashish.secure_order_api.entity.Order;
import com.Kashish.secure_order_api.repository.CustomerRepository;
import com.Kashish.secure_order_api.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest 
{
	
	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private AuditLogService auditLogService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private OrderService orderService;
	
	@Mock
	private Authentication authentication;
	
	@Mock
	private SecurityContext securityContext;
	
	
	@Test
	void createOrder_shouldSaveOrderForAdmin()
	{
	    Order order = new Order();
	    order.setTotalAmount(5000.0);
	    order.setStatus("CONFIRMED");

	    when(securityContext.getAuthentication()).thenReturn(authentication);

	    when(authentication.getAuthorities())
	        .thenAnswer(invocation ->List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

	    when(authentication.getName()).thenReturn("admin");

	    when(orderRepository.save(order)).thenReturn(order);

	    SecurityContextHolder.setContext(securityContext);

	    Order result = orderService.createOrder(order);

	    assertNotNull(result);
	    assertEquals(5000.0, result.getTotalAmount());

	    verify(orderRepository).save(order);
	    verify(auditLogService).log("admin", "CREATE_ORDER");
	}
	
	
	

}
