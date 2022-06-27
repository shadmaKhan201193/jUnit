package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.ReligionMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface ReligionService extends NGService {

	public ReligionMst getPrimaryKey(Long Id) throws NGException ;
	
	public ReligionMst getByReligionId(String religionId) throws NGException;
	
	public ReligionMst saveOrUpdate(String loginId, ReligionMst entity) throws NGException;

	public List<ReligionMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<ReligionMst> getByDeleted(Boolean isDeleted) throws NGException;
		
	public List<ReligionMst> updateCacheList(String authStatus) throws NGException;

	public ReligionMst getReligionIdByReligionName(String religionName)throws NGException;

}
