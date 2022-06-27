package com.itl.service.masters;

import java.util.List;

import com.itl.domain.entities.masters.AuthStatusMst;
import com.itl.exceptions.NGException;
import com.itl.service.base.NGService;

public interface AuthStatusService extends NGService {

	public AuthStatusMst getPrimaryKey(Long Id) throws NGException;

	public List<AuthStatusMst> getByAuthStatusName(String authStatusName) throws NGException;
	
	public List<AuthStatusMst> getByAuthStatusId(String authStatusId) throws NGException;
	
	public AuthStatusMst saveOrUpdate(String loginId, AuthStatusMst entity) throws NGException;
}
