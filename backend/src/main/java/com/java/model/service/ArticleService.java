package com.java.model.service;

import com.java.model.domain.Article;
import com.java.model.repository.ArticleRepository;
import com.java.model.repository.ReplyRepository;
import com.java.model.vo.ArticleVo;
import com.java.model.vo.ReplyVo;
import com.java.model.vo.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ArticleService extends CommonService {

	private ArticleRepository articleRepository;
	private ReplyRepository replyRepository;

	public ArticleService(ArticleRepository articleRepository, ReplyRepository replyRepository) {
		this.articleRepository = articleRepository;
		this.replyRepository = replyRepository;
	}

	@Nullable
	public Long save(ArticleVo articleVo) {
		Long id = null;
		try {
			Article article = convert(articleVo);
			articleRepository.save(article);
			// if succeeded, id must exist.
			id = article.getId();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return id;
	}

	// contains only Member FK
	@Nullable
	public List<ArticleVo> findAll(Pageable pageable, SearchRequest searchRequest) {
		List<ArticleVo> articleList = null;
		try {
			articleList = articleRepository
				.findAll(pageable, searchRequest)
				.stream()
				.map(this::convert)
				.collect(Collectors.toList());
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return articleList;
	}

	// contains Member FK, replies FK
	@Nullable
	public ArticleVo findById(Long id) {
		ArticleVo articleVo = null;
		try {
			Article article = articleRepository.findById(id);
			articleVo = convert(article);
			List<ReplyVo> replies = replyRepository
				.findAllRepliesByArticleId(id)
				.stream()
				.map(this::convert)
				.collect(Collectors.toList());
			articleVo.setReplies(replies);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return articleVo;
	}

	@Nullable
	public Long modify(ArticleVo articleVo) {
		Long id = null;
		try {
			Article article = articleRepository.findById(articleVo.getId());
			article.setTitle(articleVo.getTitle());
			article.setContent(articleVo.getContent());
			article.setLastModified(new Date());
			id = article.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Nullable
	public Long delete(Long id) {
		Long result = null;
		try {
			Article article = articleRepository.findById(id);
			article.setDeleted(true);
			result = article.getId();
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return result;
	}

	public int count(SearchRequest searchRequest) {
		return (int) articleRepository.count(searchRequest);
	}

}
