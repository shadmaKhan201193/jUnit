package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.OccupationMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface OccupationService extends NGService {

	public OccupationMst getPrimaryKey(Long Id) throws NGException;
	
	public OccupationMst getByOccupationId(String occupationId) throws NGException;
	
	public OccupationMst saveOrUpdate(String loginId, OccupationMst entity) throws NGException;

	public List<OccupationMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<OccupationMst> getByDeleted(Boolean isDeleted) throws NGException;
		
	public List<OccupationMst> updateCacheList(String authStatus) throws NGException;

	public String getOccupationIdByOccupationName(String occupationname)throws NGException;

}
