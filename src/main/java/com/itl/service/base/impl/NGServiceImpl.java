package com.itl.service.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
//import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.itl.dao.base.JPADAO;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public class NGServiceImpl<K, E> implements NGService {

	private JPADAO dao;

	protected Class<E> entityClass;
	private static final Logger logger = LoggerFactory
			.getLogger(NGServiceImpl.class);

	@SuppressWarnings("unchecked")
	public NGServiceImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass
				.getActualTypeArguments()[1];
	}

	public void setEntityManagerOnDao(EntityManager entityManager) {
		dao.setEntityManager(entityManager);
	}

	protected void setDAO(JPADAO<K, E> dAO) {
		this.dao = dAO;
	}

	public E find(long id) throws NGException {
		try {
			return (E) dao.findById(id);
		} catch (Exception e) {
			throw new NGException(entityClass.getName() + " - not found ",
					e);
		}
	}

	public E find(Serializable id) throws NGException {
		try {
			return (E) dao.findById(id);
		} catch (Exception e) {
			throw new NGException(entityClass.getName() + " - not found ",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public E saveOrUpdate(String loginId, Object entity) throws NGException {
		try {
			entity = (E)dao.merge(entity);
			return (E) dao.flush(entity);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DataIntegrityViolationException) {
				if (e.getCause() instanceof ConstraintViolationException ) {
					ConstraintViolationException cve = (ConstraintViolationException)e.getCause();
					logger.error("(ConstraintViolationException) ", cve.getSQLException());
					throw new NGException(entityClass.getName() + " - (ConstraintViolationException) ", cve.getSQLException());
				} else {
					logger.error("(DataIntegrityViolationException) ", e.getMessage());
					throw new NGException(entityClass.getName() + " - (DataIntegrityViolationException) ", e);
				}
			} else {
				throw new NGException(entityClass.getName() + " - ", e);
			}
		}
	}
}
