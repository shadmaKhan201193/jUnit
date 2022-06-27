package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.MaritalStatusMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface MaritalStatusService extends NGService {

	public MaritalStatusMst getPrimaryKey(Long Id) throws NGException ;
	
	public MaritalStatusMst getByMaritalStatusId(String maritalStatusId) throws NGException;
	
	public MaritalStatusMst saveOrUpdate(String loginId, MaritalStatusMst entity) throws NGException;

	public List<MaritalStatusMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<MaritalStatusMst> getByDeleted(Boolean isDeleted) throws NGException;
	
    public List<MaritalStatusMst> updateCacheList(String authStatus) throws NGException;
	
	public String getMaritalStatusIdByMaritalStatusName(String maritalStatusName)throws NGException;
}
