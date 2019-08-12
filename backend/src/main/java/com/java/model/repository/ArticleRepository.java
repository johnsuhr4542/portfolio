package com.java.model.repository;

import com.java.model.domain.Article;
import com.java.model.domain.QArticle;
import com.java.model.domain.QMember;
import com.java.model.vo.SearchRequest;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ArticleRepository extends CommonRepository {

	@PersistenceContext
	EntityManager entityManager;

	private QMember member = QMember.member;
	private QArticle article = QArticle.article;

	public void save(Article article) {
		entityManager.persist(article);
	}

	public List<Article> findAll(Pageable pageable, SearchRequest searchRequest) {
		return createQueryFactory(entityManager)
			.selectFrom(article)
			.innerJoin(article.author, member).fetchJoin()
			.where(
				searchCriteria(searchRequest)
				, article.deleted.isFalse())
			.orderBy(article.id.desc())
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.fetch();
	}

	public Article findById(Long id) {
		return createQueryFactory(entityManager)
			.selectFrom(article)
			.innerJoin(article.author, member).fetchJoin()
			.where(
				article.id.eq(id)
				, article.deleted.isFalse())
			.fetchOne();
	}

	public long count(SearchRequest searchRequest) {
		return createQueryFactory(entityManager)
			.select(article.count())
			.from(article)
			.where(
				searchCriteria(searchRequest),
				article.deleted.isFalse())
			.fetchCount();
	}

	private BooleanBuilder searchCriteria(SearchRequest searchRequest) {
		BooleanBuilder searchCriteria = new BooleanBuilder();
		String searchOption = searchRequest.getSearchOption();
		String searchValue = searchRequest.getSearchValue();
		switch( searchOption ) {
			case "author":
				searchCriteria.and(article.author.username.contains(searchValue));	break;
			case "title":
				searchCriteria.and(article.title.contains(searchValue));			break;
			case "content":
				searchCriteria.and(article.content.contains(searchValue));			break;
			default:
				searchCriteria
					.or(article.author.username.contains(searchValue))
					.or(article.title.contains(searchValue))
					.or(article.content.contains(searchValue));
		}
		return searchCriteria;
	}

}
