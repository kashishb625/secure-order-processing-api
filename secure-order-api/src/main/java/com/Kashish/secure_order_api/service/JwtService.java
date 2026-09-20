package com.Kashish.secure_order_api.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService 
{
	@Value("${jwt-secret}")
	private String secret;

	private SecretKey getSigninKey()
	{
		byte[] keyBytes=Decoders.BASE64URL.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(String username)
	{
		return Jwts.builder().subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000 *60 *60))
				.signWith(getSigninKey()).compact();
	}
	
	public String extractUsername(String token)
	{
		return Jwts.parser().verifyWith(getSigninKey())
				.build().parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
}
