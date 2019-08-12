package com.java.contorller;

import com.java.model.service.MemberService;
import com.java.model.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(path = "/member")
public class MemberController extends CommonController {

	@Autowired private MemberService memberService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/{username}")
	public ResponseEntity<?> findByUsername(@PathVariable String username, Authentication authentication) {
		if (!isAdmin(authentication)) {
			if (!authentication.getName().equals(username))
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		return Optional
			.ofNullable(memberService.findByUsername(username))
			.map(ResponseEntity::ok)
			.orElse(noContent().build());
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody MemberVo memberVo, Errors errors) {
		if (errors.hasErrors())
			return badRequest().build();

		// check redundancy
		if (memberService.exists(memberVo.getUsername()))
			return badRequest().body("given username already exists");

		return Optional
			.ofNullable(memberService.save(memberVo))
			.map(ResponseEntity::ok)
			.orElse(badRequest().build());
	}

	@PreAuthorize("isAuthenticated()")
	@PutMapping
	public ResponseEntity<?> modify(
		@Valid @RequestBody MemberVo memberVo, Errors errors, Authentication authentication) {
		if (errors.hasErrors())
			return badRequest().build();

		if (!memberVo.getUsername().equals(authentication.getName()))
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

		return Optional
			.ofNullable(memberService.modify(memberVo))
			.map(ResponseEntity::ok)
			.orElse(badRequest().build());
	}

	@PreAuthorize("isAuthenticated()")
	@DeleteMapping(path = "/{username}")
	public ResponseEntity<?> delete(@PathVariable String username, Authentication authentication) {
		if (!isAdmin(authentication) && !authentication.getName().equals(username))
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

		return Optional
			.ofNullable(memberService.delete(username))
			.map(ResponseEntity::ok)
			.orElse(badRequest().body("given account doesn't exists or already deleted."));
	}

	// Only for Development!!!
	@GetMapping(path = "/enhance/{username}")
	public ResponseEntity<?> enhanceUser(@PathVariable String username) {
		return Optional
			.ofNullable(memberService.enhance(username))
			.map(ResponseEntity::ok)
			.orElse(badRequest().build());
	}

}