package com.example.demo.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/api/login/")) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS, HEAD");
			response.setHeader("Access-Control-Allow-Headers",
					"Origin, Accept, X-Requested-With, Content-Type,Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
			response.setHeader("Access-Control-Max-Age", "86400");
			response.setHeader("Content-Type", "application/json");
			filterChain.doFilter(request, response);
		} else {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS, HEAD");
			response.setHeader("Access-Control-Allow-Headers",
					"Origin, Accept, X-Requested-With, Content-Type,Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
			response.setHeader("Access-Control-Max-Age", "86400");
			response.setHeader("Content-Type", "application/json");
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					System.out.println("******authorization******");
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decod = verifier.verify(token);
					String email = decod.getSubject();
					String[] roles = decod.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					for (String role : roles) {
						authorities.add(new SimpleGrantedAuthority(role));
					}
					System.out.println("roles de : " + email + " " + authorities);
					UsernamePasswordAuthenticationToken authentifcationToken = new UsernamePasswordAuthenticationToken(
							email, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authentifcationToken);
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					logger.error("error logging " + e.getMessage());
					System.out.println("error logging: " + e.getMessage());
					response.setHeader("error", e.getMessage());
					response.sendError(403);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
