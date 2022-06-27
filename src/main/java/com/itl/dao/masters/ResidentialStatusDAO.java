package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.ResidentialStatusMst;
import com.itl.exceptions.NGException;

public interface ResidentialStatusDAO extends JPADAO<ResidentialStatusMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public ResidentialStatusMst getPrimaryKey(Long Id) throws NGException;
	
	public List<ResidentialStatusMst> getByResidentialStatusName(String residentialStatusName) throws NGException;
	
	public List<ResidentialStatusMst> getByResidentialStatusId(String residentialStatusId) throws NGException;

	public List<ResidentialStatusMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException ;

	public List<ResidentialStatusMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getResidentialStatusIdByResidentialStatusName(String residentialStatusName) throws NGException;
	
}
