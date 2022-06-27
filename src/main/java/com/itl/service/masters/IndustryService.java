package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.IndustryMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface IndustryService extends NGService {

	public IndustryMst getPrimaryKey(Long Id) throws NGException ;

	public IndustryMst getByIndustryId(String industryId) throws NGException;
	
	public IndustryMst saveOrUpdate(String loginId, IndustryMst entity) throws NGException;
	
    public List<IndustryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<IndustryMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<IndustryMst> updateCacheList(String authStatus) throws NGException;

	public String getIndustryIdByIndustryName(String industryName)throws NGException;
}
