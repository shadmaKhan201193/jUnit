package com.itl.dao.base.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itl.dao.base.JPADAO;

@SuppressWarnings({ "unchecked" })
public class JpaDAOImpl<K, E> implements JPADAO<E, K> {

	private static final Logger logger = LoggerFactory.getLogger(JpaDAOImpl.class);
	protected Class<E> entityClass;

	EntityManagerFactory entityManagerFactory;

	EntityManager entityManager;
	
	Session session;
	
	SessionFactory sessionFactory;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public Session getSession() {
		this.session = getEntityManager().unwrap(Session.class);
		return this.session;
	}

	public void persist(E entity) {
		getEntityManager().persist(entity);
		getEntityManager().flush();
	}

	public void remove(E entity) {
		getEntityManager().remove(entity);
		getEntityManager().flush();
	}
	
	public E merge(E entity) {
		E e = getEntityManager().merge(entity);
		getEntityManager().flush();
		return e;
	}

	public void refresh(E entity) {
		getEntityManager().refresh(entity);
		getEntityManager().flush();
	}

	public E flush(E entity) {
		getEntityManager().flush();
		return entity;
	}
	
	public List<E> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ? extends Object> params) {
		javax.persistence.Query query = getEntityManager().createNamedQuery(
				name);

		for (final Map.Entry<String, ? extends Object> param : params
				.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		final List<E> result = (List<E>) query.getResultList();
		return result;
	}
	
	public List<E> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ? extends Object> params, int startFrom,
			int maxResults) {
		javax.persistence.Query query = getEntityManager().createNamedQuery(
				name);

		for (final Map.Entry<String, ? extends Object> param : params
				.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		query.setMaxResults(maxResults);
		query.setFirstResult(startFrom);

		final List<E> result = (List<E>) query.getResultList();
		return result;
	}
	
	public void flush() {
		getEntityManager().flush();
		getEntityManager().clear();
	}
	
	public E findById(K id) {
		logger.info("Inside findById");
		return getEntityManager().find(entityClass, id);
	}
	
}
