package com.itl.dao.masters;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.itl.dao.base.JPADAO;
import com.itl.domain.entities.masters.BranchMst;
import com.itl.exceptions.NGException;

public interface BranchDAO extends JPADAO<BranchMst, Long> {

	public EntityManagerFactory getEntityManagerFactory();
	
	public BranchMst getPrimaryKey(Long Id) throws NGException;
	
	public List<BranchMst> getByBranchName(String branchName) throws NGException;
	
	public List<BranchMst> getByBranchId(String branchId) throws NGException;

	public List<BranchMst> getByAuthStatus(String authStatus, Boolean isDeleted)  throws NGException;

	public List<BranchMst> getByDeleted(Boolean isDeleted) throws NGException;

	public String getBranchIdByBranchName(String branchName) throws NGException;
	
	
}
