package com.itl.dao.masters.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itl.dao.base.impl.JpaDAOImpl;
import com.itl.dao.masters.AuthStatusDAO;
import com.itl.domain.entities.masters.AuthStatusMst;
import com.itl.exceptions.NGException;

@Repository("authStatusDAO")
public class AuthStatusDAOImpl extends JpaDAOImpl<Long, AuthStatusMst> 
	implements AuthStatusDAO {

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@PersistenceContext(unitName = "PRODTECH")
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
		super.setEntityManager(entityManager);
	}

	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
		super.setEntityManager(entityManager);
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public AuthStatusMst getPrimaryKey(Long Id) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<AuthStatusMst> rMaaz = findByNamedQueryAndNamedParams("AuthStatusMst.getUniqueAuthStatus", queryParams);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz.get(0);
	}
	
	public List<AuthStatusMst> getByAuthStatusName(String authStatusName) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatusName", authStatusName);

		List<AuthStatusMst> rMaaz = findByNamedQueryAndNamedParams("AuthStatusMst.getauthStatusName", queryParams);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;
	}
	
	public List<AuthStatusMst> getByAuthStatusId(String authStatusId) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("authStatusId", authStatusId);

		List<AuthStatusMst> rMaaz = findByNamedQueryAndNamedParams("AuthStatusMst.getauthStatusId", queryParams);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;
	}
}
