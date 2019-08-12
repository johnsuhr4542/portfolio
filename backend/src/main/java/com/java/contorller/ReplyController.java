package com.java.contorller;

import com.java.model.service.ReplyService;
import com.java.model.vo.ReplyVo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping(path = "/reply")
@AllArgsConstructor
public class ReplyController extends CommonController {

	private ReplyService replyService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody ReplyVo replyVo, Errors errors) {
		if (errors.hasErrors())
			return badRequest().build();

		return Optional
			.ofNullable(replyService.save(replyVo))
			.map(ResponseEntity::ok)
			.orElse(badRequest().build());
	}

	@PreAuthorize("isAuthenticated()")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) {

		// if request is not by admin nor reply owner, reject delete task
		if (!isAdmin(authentication)) {
			ReplyVo replyVo = replyService.findById(id);
			if (!isOwner(authentication, replyVo))
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}

		// or do task
		return Optional
			.ofNullable(replyService.delete(id))
			.map(ResponseEntity::ok)
			.orElse(badRequest().build());
	}

}