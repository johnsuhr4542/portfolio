package com.java.model.jwt;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
	when issuing tokens in explicit authentication process,
	refresh token will be stored in this registry for checking later to reissue access token.
 */
@Component
public class TokenRegistry {

	private Map<String, String> registry;

	public TokenRegistry() {
		this.registry = new HashMap<>();
	}

	public void add(String username, String refreshToken) {
		this.registry.put(username, refreshToken);
	}

	public String get(String username) {
		return this.registry.get(username);
	}

}
