package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.StateMst;
import com.itl.exceptions.NGException;

public interface StateDAO extends JPADAO<StateMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public StateMst getPrimaryKey(Long Id) throws NGException;
	
	public List<StateMst> getByStateName(String stateName) throws NGException;
	
	public List<StateMst> getByStateId(String stateId) throws NGException;
	
	public List<StateMst> getStateByCountryId(String countryId, String stateId) throws NGException;
	
	public List<StateMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<StateMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getStateIdByStateName(String stateName) throws NGException;
}
