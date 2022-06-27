package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.BranchMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface BranchService extends NGService {

	public BranchMst getPrimaryKey(Long Id) throws NGException;
	
	public  BranchMst getByBranchId(String branchId) throws NGException;
	
	public BranchMst saveOrUpdate(String loginId, BranchMst entity) throws NGException;

	public List<BranchMst> getByAuthStatus(String authStatus, Boolean isDeleted) throws NGException;
	
	public List<BranchMst> getByDeleted(Boolean isDeleted)  throws NGException;
	
	public List<BranchMst> updateCacheList(String authStatus) throws NGException;
	
	public String getBranchIdByBranchName(String branchName) throws NGException;
	
	


}
