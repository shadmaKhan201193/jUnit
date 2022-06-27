package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.OccupationMst;
import com.itl.exceptions.NGException;

public interface OccupationDAO extends JPADAO<OccupationMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public OccupationMst getPrimaryKey(Long Id) throws NGException;
	
	public List<OccupationMst> getByOccupationName(String occupationName) throws NGException;
	
	public List<OccupationMst> getByOccupationId(String occupationId) throws NGException;

	public List<OccupationMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<OccupationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public String getOccupationIdByOccupationName(String occupationName) throws NGException;

}
