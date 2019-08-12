package com.java.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public abstract class CommonRepository {

	protected JPAQueryFactory createQueryFactory(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}

}
