package com.mx.jiraiya.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.jiraiya.persistence.entities.Usuario;
import com.mx.jiraiya.filter.service.JWTService;
import com.mx.jiraiya.filter.service.JWTServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	Logger logger = LogManager.getLogger(JWTAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;
	private JWTService jwtService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
		this.jwtService = jwtService;
		logger.info("JWTAuthenticationFilter this.jwtService" + this.jwtService);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = obtainUsername(request);
		String password = obtainPassword(request);
		logger.info("JWTAuthenticationFilter-->attemptAuthentication");
		if (username != null && password != null) {
			logger.info("Username desde request parametrer (form-data) " + username);
			logger.info("Password desde request parametrer (form-data) " + password);
		} else {
			Usuario user = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

				username = user.getUsername();
				password = user.getPassword();

				logger.info("Username desde request  (raw) " + username);
				logger.info("Password desde request  (raw) " + password);

			} catch (IOException e) {
				logger.error("Error de autenticación verificar campos.");
				Map<String, Object> body = new HashMap<String, Object>();
				body.put("mensaje", "Error de autenticación.");
				body.put("error", "verificar campos");

				try {
					response.getWriter().write(new ObjectMapper().writeValueAsString(body));
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				response.setStatus(401);
				response.setContentType("application/json");

				return null;

			}
		}
		username = username.trim();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		logger.info("El token " + authToken + "En authenticationManager" + authenticationManager.authenticate(authToken));
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		logger.info("successfulAuthentication");

		String token = jwtService.create(authResult);

		response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.PREFIJO_TOKEN + token);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", authResult.getName()));
		logger.info("El body " + body);
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticación.");
		body.put("error", failed.getMessage());
		logger.error("unsuccessfulAuthentication + body " + body);
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}

}
