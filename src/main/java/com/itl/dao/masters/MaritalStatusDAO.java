package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.MaritalStatusMst;
import com.itl.exceptions.NGException;

public interface MaritalStatusDAO extends JPADAO<MaritalStatusMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public MaritalStatusMst getPrimaryKey(Long Id) throws NGException;
	
	public List<MaritalStatusMst> getByMaritalStatusName(String maritalStatusName) throws NGException;
	
	public List<MaritalStatusMst> getByMaritalStatusId(String maritalStatusId) throws NGException;
	
	public List<MaritalStatusMst> getByAuthStatus(String authAuthorized, Boolean isDeleted);

	public List<MaritalStatusMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getMaritalStatusIdByMaritalStatusName(String maritalStatusName) throws NGException;

}