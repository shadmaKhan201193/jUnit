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
import com.itl.dao.masters.AddressTypeDAO;
import com.itl.domain.entities.masters.AddressType;
import com.itl.exceptions.NGException;

@Repository("addressTypeDAO")
public class AddressTypeDAOImpl extends JpaDAOImpl<Long, AddressType> 
	implements AddressTypeDAO {

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
	
	public AddressType getPrimaryKey(Long Id) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("Id", Id);

		List<AddressType> rMaaz = findByNamedQueryAndNamedParams("AddressTypeMst.getUniqueAddressType", queryParams);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz.get(0);
	}
	
	public List<AddressType> getByAddressTypeName(String AddressTypeName) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("addressTypeName", AddressTypeName);

		List<AddressType> rMaaz = findByNamedQueryAndNamedParams("AddressTypeMst.getAddressTypeName", queryParams);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;
	}
	
	public List<AddressType> getByAddressTypeId(String AddressTypeId) throws NGException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("addressTypeId", AddressTypeId);

		List<AddressType> rMaaz = findByNamedQueryAndNamedParams("AddressTypeMst.getAddressTypeId", queryParams);
		if (null == rMaaz) {
			return null;
		} else if (rMaaz != null && rMaaz.size() == 0) {
			return null;
		}
		return rMaaz;
	}
}
