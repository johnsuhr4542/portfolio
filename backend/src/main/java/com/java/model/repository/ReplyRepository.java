package com.java.model.repository;

import com.java.model.domain.QArticle;
import com.java.model.domain.QMember;
import com.java.model.domain.QReply;
import com.java.model.domain.Reply;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReplyRepository extends CommonRepository {

	@PersistenceContext
	EntityManager entityManager;

	private QMember member = QMember.member;
	private QArticle article = QArticle.article;
	private QReply reply = QReply.reply;

	public void save(Reply reply) {
		entityManager.persist(reply);
	}

	public Reply findById(Long id) {
		return createQueryFactory(entityManager)
			.selectFrom(reply)
			.innerJoin(reply.author, member).fetchJoin()
			.where(
				reply.id.eq(id)
				, reply.deleted.isFalse())
			.fetchOne();
	}

	public List<Reply> findAllRepliesByArticleId(Long articleId) {
		return createQueryFactory(entityManager)
			.selectFrom(reply)
			.innerJoin(reply.author, member).fetchJoin()
			.where(
				reply.article.id.eq(articleId)
				, reply.deleted.isFalse())
			.orderBy(reply.id.asc())
			.fetch();
	}

}
