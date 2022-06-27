package com.itl.service.base;

import javax.persistence.EntityManager;

import com.itl.exceptions.NGException;

public interface NGService<Long, E> {

	public void setEntityManagerOnDao(EntityManager entityManager);

	public E saveOrUpdate(String loginId, E entity) throws NGException;
}
