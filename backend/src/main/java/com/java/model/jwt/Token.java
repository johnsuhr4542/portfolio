package com.java.model.jwt;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class Token {
	private UserDetails userDetails;
	private String accessToken;
	private String refreshToken;
}
