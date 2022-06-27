package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.SectorMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface SectorService extends NGService {

	public SectorMst getPrimaryKey(Long Id) throws NGException ;
	
	public SectorMst getBySectorId(String sectorId) throws NGException;
	
	public SectorMst saveOrUpdate(String loginId, SectorMst entity) throws NGException;

	public List<SectorMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<SectorMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<SectorMst> updateCacheList(String authStatus) throws NGException;

	public String getSectorIdBySectorName(String sectorName)throws NGException;
}
