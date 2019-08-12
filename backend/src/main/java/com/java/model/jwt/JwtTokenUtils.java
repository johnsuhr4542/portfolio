package com.java.model.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtTokenUtils {

	@Value("${jwt.subject}")
	private String subject;
	@Value("${jwt.signingKey}")
	private String signingKey;

	private static final int MINUTE = 60;
	private static final int WEEK = 60 * 60 * 24 * 7;

	public String createAccessToken(UserDetails userDetails) {
		return issueToken(userDetails, 30 * MINUTE);
	}

	public String createRefreshToken(UserDetails userDetails) {
		return issueToken(userDetails, 2 * WEEK);
	}

	public String getUsername(String jwt) {
		return (String) getClaims(jwt)
			.get("username");
	}

	public String getId(String jwt) {
		return getClaims(jwt)
			.getId();
	}

	public UserDetails getUserDetails(String jwt) {
		return User.builder()
			.username(getUsername(jwt))
			.password("")
			.authorities(getAuthorities(jwt))
			.build();
	}

	@SuppressWarnings("unchecked")
	public Collection<? extends GrantedAuthority> getAuthorities(String jwt) {
		return ((List<String>) getClaims(jwt).get("authorities"))
			.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}

	private String issueToken(UserDetails userDetails, int period) {
		return Jwts.builder()
			.setSubject(subject)
			.setId(UUID.randomUUID().toString().replace("-", ""))
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * period))
			.claim("username", userDetails.getUsername())
			.claim("authorities",
				userDetails.getAuthorities()
					.stream()
					.map(Object::toString)
					.collect(Collectors.toList()))
			.signWith(SignatureAlgorithm.HS512, signingKey.getBytes())
			.compact();
	}

	private Claims getClaims(String jwt) {
		return Jwts.parser()
			.setSigningKey(signingKey.getBytes())
			.parseClaimsJws(jwt)
			.getBody();
	}

}
