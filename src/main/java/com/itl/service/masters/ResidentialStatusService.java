package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.ResidentialStatusMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface ResidentialStatusService extends NGService {

	public ResidentialStatusMst getPrimaryKey(Long Id) throws NGException ;
	
	public ResidentialStatusMst getByResidentialStatusId(String residentialStatusId) throws NGException;
	
	public ResidentialStatusMst saveOrUpdate(String loginId, ResidentialStatusMst entity) throws NGException;
	
	public List<ResidentialStatusMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<ResidentialStatusMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<ResidentialStatusMst> updateCacheList(String authStatus) throws NGException;

	public String getResidentialStatusIdByResidentialStatusName(String residentialStatusName)throws NGException;

}
