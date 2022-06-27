package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.StateMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface StateService extends NGService {

	public StateMst getPrimaryKey(Long Id) throws NGException ;
	
	public StateMst getByStateId(String stateId) throws NGException;
	
	public StateMst saveOrUpdate(String loginId, StateMst entity) throws NGException;
	
	public List<StateMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<StateMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<StateMst> updateCacheList(String authStatus) throws NGException;

	public String getStateIdByStateName(String stateName)throws NGException;
		
}
