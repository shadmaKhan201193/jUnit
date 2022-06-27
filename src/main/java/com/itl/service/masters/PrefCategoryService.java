package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.PrefCategoryMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface PrefCategoryService extends NGService {

	public PrefCategoryMst getPrimaryKey(Long Id) throws NGException ;
	
	public PrefCategoryMst getByPrefCategoryId(String prefCategoryId) throws NGException;
	
	public PrefCategoryMst saveOrUpdate(String loginId, PrefCategoryMst entity) throws NGException;

	public List<PrefCategoryMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
		
	public List<PrefCategoryMst> getByDeleted(Boolean isDeleted) throws NGException;
	
	public List<PrefCategoryMst> updateCacheList(String authStatus) throws NGException;

	public String getPrefCategoryIdByPrefCategoryName(String prefCategoryName)throws NGException;
}
