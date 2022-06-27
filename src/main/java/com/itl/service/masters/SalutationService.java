package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.SalutationMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface SalutationService extends NGService {

	public SalutationMst getPrimaryKey(Long Id) throws NGException ;
	
	public SalutationMst getBySalutationId(String salutationId) throws NGException;
	
	public SalutationMst saveOrUpdate(String loginId, SalutationMst entity) throws NGException;

	public List<SalutationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<SalutationMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<SalutationMst> updateCacheList(String authStatus) throws NGException;

	public String getSalutationIdBySalutationName(String salutationName)throws NGException;
}
