package com.java.model.repository;

import com.java.model.domain.Member;
import com.java.model.domain.QMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository extends CommonRepository {

	@PersistenceContext
	EntityManager entityManager;

	private QMember member = QMember.member;

	public void save(Member member) {
		entityManager.persist(member);
	}

	public Member findByUsername(String username) {
		return createQueryFactory(entityManager)
			.selectFrom(member)
			.where(
				member.username.eq(username),
				member.deleted.isFalse())
			.fetchOne();
	}

	public void enhance(String username) {
		createQueryFactory(entityManager)
			.update(member)
			.set(member.authority, "ROLE_ADMIN")
			.where(member.username.eq(username))
			.execute();
	}

}
