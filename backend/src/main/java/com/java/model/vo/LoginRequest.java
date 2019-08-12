package com.java.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
	@NotNull
	@Size(min = 4, max = 24)
	private String username;
	@NotNull
	@Size(min = 4, max = 64)
	private String password;
}
