package com.itl.dao.base;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public interface JPADAO<E, K> {

	public void persist(E entity);
	
	public void remove(E entity);

	public E merge(E entity);

	public void refresh(E entity);
	
	public void setEntityManager(EntityManager entityManager);

	public E flush(E entity);
	
	public List<E> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ? extends Object> params);
	
	public List<E> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ? extends Object> params, int startFrom,
			int maxResults);
	
	public void flush();
	
	public E findById(K id);
	
}
