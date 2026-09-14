package com.dinesh.LibraryManagementSystem.configurations;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(JwtConstant.JWT_HEADER);

		if (jwt != null) {

			jwt = jwt.substring(7);

			try {

				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

				System.out.println("JWT Token: " + jwt);

			} catch (Exception e) {

				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid JWT token");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}