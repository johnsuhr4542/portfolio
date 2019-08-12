package com.java.contorller;

import com.java.model.jwt.JwtTokenUtils;
import com.java.model.jwt.Token;
import com.java.model.jwt.TokenRegistry;
import com.java.model.vo.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/auth")
@Slf4j
public class AuthenticationController {

	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private JwtTokenUtils jwtTokenUtils;
	@Autowired private TokenRegistry tokenRegistry;

	@PostMapping(path = "/token")
	public ResponseEntity token(@Valid @RequestBody LoginRequest loginRequest, Errors errors) {
		if (errors.hasErrors())
			return badRequest().build();
		Token compositeToken = new Token();
		try {
			String username = loginRequest.getUsername();
			String password = loginRequest.getPassword();

			UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(username, password);
			// failed to authentication, AuthenticationException will be thrown...
			Authentication authentication = authenticationManager.authenticate(token);

			// issue tokens.
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String accessToken = jwtTokenUtils.createAccessToken(userDetails);
			String refreshToken = jwtTokenUtils.createRefreshToken(userDetails);

			compositeToken.setUserDetails(userDetails);
			compositeToken.setAccessToken(accessToken);
			compositeToken.setRefreshToken(refreshToken);

			// storing refresh token for later use.
			tokenRegistry.add(username, refreshToken);

		} catch (Exception e) {
			log.warn("authentication failed >> {}", e.getMessage());
			return badRequest().body("failed to authenticate");
		}
		return ok(compositeToken);
	}

	@PostMapping(path = "/refresh")
	public ResponseEntity refresh(@RequestBody Token token) {
		if (token == null)
			return badRequest().body("requires tokens");

		Token compositeToken = new Token();

		try {
			String refreshToken = token.getRefreshToken();
			String username = jwtTokenUtils.getUsername(refreshToken);
			String savedToken = tokenRegistry.get(username);

			String savedTokenId = jwtTokenUtils.getId(savedToken);
			String refreshTokenId = jwtTokenUtils.getId(refreshToken);

			if (!savedTokenId.equals(refreshTokenId))
				return badRequest().body("invalid token");

			UserDetails userDetails = jwtTokenUtils.getUserDetails(refreshToken);
			String accessToken = jwtTokenUtils.createAccessToken(userDetails);
			compositeToken.setAccessToken(accessToken);

		} catch (Exception e) {
			log.warn("failed to refresh token >> {}", e.getMessage());
			return badRequest().body("failed to reissue token");
		}
		return ok(compositeToken);
	}

}
