package com.java.model.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired private JwtTokenUtils jwtTokenUtils;
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		// simple cors filter
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS");
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

		// if request is pre-flight, return response with 200 ok
		if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		// if request is for authentication, skip this filter
		if (request.getRequestURI().contains("/auth")) {
			filterChain.doFilter(request, response);
			return;
		}

		// start authentication process
		String header = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(header) && header.startsWith(TOKEN_PREFIX)) {
			try {
				// parse token
				String jwt = header.substring(TOKEN_PREFIX.length());
				UserDetails userDetails = jwtTokenUtils.getUserDetails(jwt);

				// create authentication
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// process actual authentication
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

		filterChain.doFilter(request, response);

	}
}
