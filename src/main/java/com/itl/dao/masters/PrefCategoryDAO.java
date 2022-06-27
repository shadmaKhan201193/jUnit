package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.PrefCategoryMst;
import com.itl.exceptions.NGException;

public interface PrefCategoryDAO extends JPADAO<PrefCategoryMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public PrefCategoryMst getPrimaryKey(Long Id) throws NGException;
	
	public List<PrefCategoryMst> getByPrefCategoryName(String prefCategoryName) throws NGException;
	
	public List<PrefCategoryMst> getByPrefCategoryId(String prefCategoryId) throws NGException;

	public List<PrefCategoryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;

	public List<PrefCategoryMst> getByDeleted(Boolean isDeleted)throws NGException;

	public String getPrefCategoryIdByPrefCategoryName(String prefCategoryName) throws NGException;
	
	
	
}
