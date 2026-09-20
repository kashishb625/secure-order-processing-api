package com.Kashish.secure_order_api.security;

import java.io.IOException;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Kashish.secure_order_api.entity.User;
import com.Kashish.secure_order_api.service.JwtService;
import com.Kashish.secure_order_api.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationfilter extends OncePerRequestFilter
{

	private final JwtService jwtService;
	private final UserService userService;
	
	public JwtAuthenticationfilter(JwtService jwtService, UserService userService) 
	{
		this.jwtService = jwtService;
		this.userService=userService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String authHeader=request.getHeader("Authorization");
		if(authHeader !=null && authHeader.startsWith("Bearer "))
		{
			try {
			String token=authHeader.substring(7);
			
			String username=jwtService.extractUsername(token);
			
			User user=userService.getUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authentication=
					new UsernamePasswordAuthenticationToken(username, null, 
							java.util.List.of(
									new SimpleGrantedAuthority("ROLE_"+user.getRole())));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch(io.jsonwebtoken.JwtException | IllegalArgumentException ex)
		{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid or expired token");
				return;
		}
	}
		
		filterChain.doFilter(request, response);
		
 }
}
