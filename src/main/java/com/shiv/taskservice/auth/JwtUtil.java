package com.shiv.taskservice.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

	private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // auto-generate a secure key

	private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuer("your-app").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(secretKey).compact();
	}

	public String validateTokenAndGetUsername(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

			return claims.getSubject(); // returns the username
		} catch (JwtException e) {
			// Token is invalid or expired
			return null;
		}
	}
}
