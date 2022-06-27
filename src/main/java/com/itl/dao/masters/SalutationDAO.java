package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.SalutationMst;
import com.itl.exceptions.NGException;

public interface SalutationDAO extends JPADAO<SalutationMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public SalutationMst getPrimaryKey(Long Id) throws NGException;
	
	public List<SalutationMst> getBySalutationName(String salutationName) throws NGException;
	
	public List<SalutationMst> getBySalutationId(String salutationId) throws NGException;

	public List<SalutationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;

	public List<SalutationMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getSalutationIdBySalutationName(String salutationName)throws NGException;
	
	
}
