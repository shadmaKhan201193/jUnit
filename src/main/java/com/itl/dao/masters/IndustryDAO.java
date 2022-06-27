package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.IndustryMst;
import com.itl.exceptions.NGException;

public interface IndustryDAO extends JPADAO<IndustryMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public IndustryMst getPrimaryKey(Long Id) throws NGException;
	
	public List<IndustryMst> getByIndustryName(String industryName) throws NGException;
	
	public List<IndustryMst> getByIndustryId(String industryId) throws NGException;
	
	public List<IndustryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<IndustryMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getIndustryIdByIndustryName(String industryName) throws NGException;

	
	
}
