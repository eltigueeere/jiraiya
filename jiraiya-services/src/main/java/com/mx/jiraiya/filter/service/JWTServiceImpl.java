package com.mx.jiraiya.filter.service;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.jiraiya.filter.SimpleGrantedAuthorityMixim;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTServiceImpl implements JWTService {
	
	public static final long FECHA_DE_EXPIRACION = 3600000;
	
	public static final String PREFIJO_TOKEN =  "Bearer ";
	
	public static final String HEADER_STRING = "Authorization";

	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	@Override
	public String create(Authentication auth) throws IOException {

		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setClaims(claims).setSubject(auth.getName()).signWith(SECRET_KEY)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + FECHA_DE_EXPIRACION )).compact();
		
		return token;
	}

	@Override
	public boolean validate(String token) {
		try {
			getClaims(token);
			System.out.println("Token true");
			return true;

		} catch (JwtException | IllegalArgumentException e) {
			System.out.println("Token FALSE" + e);
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		@SuppressWarnings("deprecation")
		Claims claims = Jwts.parser()
	            .setSigningKey(SECRET_KEY)
	            .parseClaimsJws(resolve(token)).getBody();
				/*(Claims) Jwts
			    .parserBuilder().setSigningKey(SECRET_KEY)
			    .build()
			    .parseClaimsJws( resolve(token) );*/
		return claims;
	}

	@Override
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixim.class)
						.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

		return authorities;
	}

	@Override
	public String resolve(String token) {
		if (token != null && token.startsWith(PREFIJO_TOKEN)) {
			return token.replace(PREFIJO_TOKEN, "");
		}
		return null;
	}

}
