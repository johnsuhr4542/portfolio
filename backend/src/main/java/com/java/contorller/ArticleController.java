package com.java.contorller;

import com.java.model.service.ArticleService;
import com.java.model.util.FileUtil;
import com.java.model.util.Pagination;
import com.java.model.vo.ArticleVo;
import com.java.model.vo.SearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(path = "/article")
@AllArgsConstructor
public class ArticleController extends CommonController {

	private ArticleService articleService;
	private FileUtil fileUtil;

	@GetMapping
	public ResponseEntity findAll(@PageableDefault(page = 1) Pageable pageable, SearchRequest searchRequest) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, 10);
		int count = articleService.count(searchRequest);
		Pagination pagination = new Pagination(pageable.getPageNumber(), pageable.getPageSize(), count);

		return Optional
			.ofNullable(articleService.findAll(pageRequest, searchRequest))
			.map(articles -> {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("articles", articles);
				resultMap.put("pagination", pagination);
				return ok(resultMap);
			})
			.orElse(noContent().build());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity findById(@PathVariable Long id) {
		return Optional
			.ofNullable(articleService.findById(id))
			.map(ResponseEntity::ok)
			.orElse(noContent().build());
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping
	public ResponseEntity save(@Valid @RequestBody ArticleVo articleVo, Errors errors) {
		if (errors.hasErrors())
			return badRequest().build();
		return Optional
			.ofNullable(articleService.save(articleVo))
			.map(ResponseEntity::ok)
			.orElse(badRequest().build());
	}

	@PreAuthorize("isAuthenticated()")
	@PutMapping
	public ResponseEntity<?> modify(
		@Valid @RequestBody ArticleVo articleVo, Errors errors, Authentication authentication) {
		if (errors.hasErrors())
			return badRequest().build();

		if (!authentication.getName().equals(articleVo.getAuthor().getUsername()))
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

		return Optional
			.ofNullable(articleService.modify(articleVo))
			.map(ResponseEntity::ok)
			.orElse(badRequest().build());
	}

	@PreAuthorize("isAuthenticated()")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) {
		if (!isAdmin(authentication)) {
			ArticleVo articleVo = articleService.findById(id);
			if (!isOwner(authentication, articleVo))
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return Optional
			.ofNullable(articleService.delete(id))
			.map(ResponseEntity::ok)
			.orElse(badRequest().build());
	}

	@PostMapping(path = "/upload")
	public ResponseEntity upload(@RequestPart MultipartFile upload, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		return Optional
			.ofNullable(fileUtil.save(upload))
			.map(path -> {
				resultMap.put("uploaded", true);
				String locator = extractLocator(request);
				resultMap.put("url", locator + path);
				return ok(resultMap);
			})
			.orElseGet(() -> {
				resultMap.put("uploaded", false);
				resultMap.put("error", "couldn't upload file");
				return ok(resultMap);
			});
	}

}